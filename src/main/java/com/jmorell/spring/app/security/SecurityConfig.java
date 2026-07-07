package com.jmorell.spring.app.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

// Configuración principal de seguridad de Spring.
// Aquí definimos:
// 1) Qué URLs se protegen y con qué roles.
// 2) Cómo se autentica el usuario (UserDetailsService + PasswordEncoder).
// 3) Qué beans de seguridad debe registrar Spring.
@Configuration
@EnableWebSecurity
public class SecurityConfig {

        // Servicio que carga usuarios desde BD (implementación de UserDetailsService).
        private final UserDetailsService userDetailsService;

        public SecurityConfig(UserDetailsService userDetailsService) {
                this.userDetailsService = userDetailsService;
        }

        // Cadena de filtros de seguridad (SecurityFilterChain):
        // es el punto central donde se configura autorización, login, errores y logout.
        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
                http
                                // Indicamos qué AuthenticationProvider usará Spring Security para validar
                                // usuario/contraseña.
                                .authenticationProvider(authenticationProvider())
                                // Aquí indicamos qué rutas puede abrir cada tipo de usuario.
                                .authorizeHttpRequests(auth -> auth
                                                // Recursos estáticos (Bootstrap, FontAwesome, etc. via WebJars).
                                                .requestMatchers("/webjars/**", "/css/**", "/js/**")
                                                .permitAll()
                                                // La pantalla de login debe poder verla cualquiera.
                                                .requestMatchers("/errores/403")
                                                .permitAll()
                                                // La pantalla de error 403 y el login deben poder verse sin iniciar
                                                // sesión.
                                                .requestMatchers("/login")
                                                .permitAll()
                                                // Solo un ADMIN puede crear, editar, guardar o borrar personas.
                                                // Estas son las rutas reales que usa el controlador del proyecto.
                                                .requestMatchers("/personas/add", "/personas/edit/**", "/personas/save",
                                                                "/personas/guardar",
                                                                "/personas/delete/**")
                                                .hasRole("ADMIN")
                                                // A la raíz y al listado de personas puede entrar USER o ADMIN.
                                                .requestMatchers("/", "/personas", "/personas/")
                                                .hasAnyRole("USER", "ADMIN")
                                                // Cualquier otra petición requiere haber iniciado sesión.
                                                .anyRequest()
                                                .authenticated())

                                // Si activas esto, se usaría el login HTML por defecto de Spring.
                                // .formLogin(login -> login.permitAll());

                                // Le decimos a Spring que use nuestra vista /login en vez de la suya.
                                .formLogin(login -> login
                                                .loginPage("/login")
                                                // Si el usuario o la contraseña fallan, vuelve al login con ?error.
                                                .failureUrl("/login?error=true")
                                                // Si el login va bien, entra a la página principal.
                                                .defaultSuccessUrl("/", true)
                                                .permitAll())
                                // Si el usuario sí está logueado pero no tiene permisos, mostramos 403.
                                .exceptionHandling(exception -> exception
                                                .accessDeniedPage("/errores/403"))
                                .logout(logout -> logout
                                                // Si cierra sesión, vuelve al login con mensaje de salida.
                                                .logoutSuccessUrl("/login?logout=true")
                                                .permitAll());

                // Construye y devuelve la configuración final de seguridad.
                return http.build();
        }

        // Define el algoritmo de hash de contraseñas.
        // BCrypt es el recomendado actualmente para aplicaciones web.
        @Bean
        public PasswordEncoder passwordEncoder() {
                return new BCryptPasswordEncoder();
        }

        // Provider de autenticación "DAO":
        // compara el usuario cargado por userDetailsService con la contraseña recibida
        // usando el PasswordEncoder configurado arriba.
        @Bean
        public DaoAuthenticationProvider authenticationProvider() {
                DaoAuthenticationProvider provider = new DaoAuthenticationProvider(userDetailsService);
                provider.setPasswordEncoder(passwordEncoder());
                return provider;
        }

        // Expone AuthenticationManager como bean para poder inyectarlo en otros sitios
        // (por ejemplo, en login personalizado o endpoints de autenticación).
        // Spring lo construye a partir de la configuración anterior.
        @Bean
        public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
                return authConfig.getAuthenticationManager();
        }

}

// Perfecto. Aquí tienes el mapa mental del login en tu configuración actual:

// 1) Llega una petición protegida
// El SecurityFilterChain intercepta todo; si la URL requiere auth y no hay
// sesión, redirige a /login.

// 2) Usuario envía formulario de login
// Con .formLogin(...), Spring procesa usuario/password automáticamente.

// 3) Spring delega al AuthenticationManager
// El manager coordina la autenticación usando los providers registrados.

// 4) Entra DaoAuthenticationProvider
// Tu bean authenticationProvider() recibe las credenciales y llama a
// UserDetailsService para cargar el usuario desde BD.

// 5) Comparación de contraseña
// El provider usa PasswordEncoder (BCryptPasswordEncoder) para comparar:

// password del formulario (plana)
// hash guardado en BD
// 6) Resultado

// Si coincide: autenticación OK, crea contexto de seguridad y te manda a /
// (defaultSuccessUrl("/", true)).
// Si falla: vuelve a /login?error=true.
// Si autenticado sin permiso: /errores/403.
// Mini resumen de roles en tu config:

// /personas/add, /personas/edit/**, /personas/save, /personas/delete/** ->
// ADMIN
// /, /personas, /personas/ -> USER o ADMIN
// /login y /errores/403 -> público
// Si quieres, te hago ahora un diagrama ASCII del flujo completo con flechas
// para que lo tengas como chuleta en clase/apuntes.

// si

// [ Navegador ]
// |
// | 1) GET /personas
// v
// [ SecurityFilterChain ]
// |
// |-- ¿URL pública? (/login, /errores/403) --> deja pasar
// |
// |-- ¿URL requiere auth? --> SÍ
// | |
// | |-- ¿Usuario autenticado? NO
// | v
// | redirige a /login
// |
// v
// [ Formulario Login (/login) ]
// |
// | 2) POST usuario/password
// v
// [ UsernamePasswordAuthenticationFilter ]
// |
// | 3) crea Authentication y llama a:
// v
// [ AuthenticationManager ]
// |
// | 4) delega en:
// v
// [ DaoAuthenticationProvider ]
// |
// |-- llama a UserDetailsService.loadUserByUsername(username)
// | |
// | v
// | [ BD / Repositorio ] -> devuelve UserDetails + hash password + roles
// |
// |-- 5) PasswordEncoder (BCrypt) compara:
// | password_form vs hash_BD
// |
// |-- ¿Coincide?
// | |
// | |-- NO -> fallo -> /login?error=true
// | |
// | |-- SÍ -> éxito
// v
// [ SecurityContext + Sesión ]
// |
// | 6) Redirección success
// v
// [ "/" (defaultSuccessUrl) ]
// |
// | 7) En siguientes requests:
// | SecurityFilterChain revisa roles de la sesión
// |
// |-- si tiene rol correcto -> entra
// |-- si está logueado pero sin rol -> /errores/403
// Chuleta rápida de tu proyecto:

// SecurityFilterChain: reglas de rutas + login/logout + manejo 403.
// DaoAuthenticationProvider: autenticación contra UserDetailsService.
// UserDetailsService: carga usuario/roles desde base de datos.
// PasswordEncoder (BCrypt): valida contraseña de forma segura.
// AuthenticationManager: orquesta todo el proceso de autenticación.
