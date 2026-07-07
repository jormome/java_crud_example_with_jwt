# Spring Security Básico - ControlClientes

Este proyecto es una aplicación web de gestión de clientes con Spring Boot, Spring Security, Spring Data JPA y Thymeleaf.

## Arquitectura

- `com.curso.spring.app.AppApplication` - clase principal de arranque de Spring Boot.
- `com.curso.spring.app.web` - controladores web, manejo de rutas y páginas.
- `com.curso.spring.app.security` - configuración de seguridad, manejo de usuarios y roles.
- `com.curso.spring.app.domain` - entidades JPA que representan el modelo de datos.
- `com.curso.spring.app.dao` - repositorios de acceso a datos.
- `com.curso.spring.app.dto` - objetos de transferencia de datos (DTOs).
- `com.curso.spring.app.mappers` - mapeos entre entidades y DTOs con MapStruct.
- `com.curso.spring.app.services` - lógica de negocio y servicios de la aplicación.
- `com.curso.spring.app.utils` - utilidades y componentes compartidos.

## Tecnologías clave

- Spring Boot 4
- Spring Security
- Spring Data JPA
- Thymeleaf
- Bootstrap (WebJars)
- MapStruct
- MySQL (configuración principal)

## Configuración segura

El archivo `src/main/resources/application.properties` utiliza variables de entorno para no exponer credenciales en el repositorio:

```properties
spring.datasource.username=${DB_USER:root}
spring.datasource.password=${DB_PASS:}
```

Para ejecutar localmente, configure las variables de entorno antes de iniciar la aplicación.

## Ejecución

```bash
./mvnw clean package
./mvnw spring-boot:run
```

## Notas para GitHub

- `target/` ya está ignorado en `.gitignore`.
- Se agregaron reglas para ignorar archivos de configuración local como `application-local.properties` y `.env`.
- No se comparten credenciales sensibles en el repositorio.

- [x] Arquitectura en capas (Routers, Services, Repositories)
- [x] Autenticación JWT y Hashing
- [x] Logging estructurado en JSON y Request ID
- [ ] Tests unitarios y de integración con `pytest` (En proceso)
- [ ] Dockerización de la aplicación (En proceso)
