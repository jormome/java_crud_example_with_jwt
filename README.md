# Spring Security Básico - ControlClientes

Este proyecto es una aplicación web de gestión de clientes con Spring Boot, Spring Security, Spring Data JPA y Thymeleaf.

## Arquitectura

- `com.jmorell.spring.app.AppApplication` - clase principal de arranque de Spring Boot.
- `com.jmorell.spring.app.web` - controladores web, manejo de rutas y páginas.
- `com.jmorell.spring.app.security` - configuración de seguridad, manejo de usuarios y roles.
- `com.jmorell.spring.app.domain` - entidades JPA que representan el modelo de datos.
- `com.jmorell.spring.app.dao` - repositorios de acceso a datos.
- `com.jmorell.spring.app.dto` - objetos de transferencia de datos (DTOs).
- `com.jmorell.spring.app.mappers` - mapeos entre entidades y DTOs con MapStruct.
- `com.jmorell.spring.app.services` - lógica de negocio y servicios de la aplicación.
- `com.jmorell.spring.app.utils` - utilidades y componentes compartidos.

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

## 🗺️ Roadmap de Mejora Continua (En Proceso)

Para llevar esta aplicación al siguiente nivel de madurez de software, me encuentro ejecutando activamente las siguientes mejoras en el sistema:

### 🧪 FASE 1: Cobertura de Pruebas y Calidad de Código 🔄 *(Fase Actual)*
- [ ] **Pruebas Unitarias con JUnit 5 & Mockito:** Implementar test de componentes core aislados (servicios y lógica de negocio) emulando el comportamiento de la capa de persistencia mediante mocks.
- [ ] **Pruebas de Integración con `@SpringBootTest`:** Añadir test de integración con bases de datos en memoria (H2) o Testcontainers para validar el comportamiento real de los repositorios y controladores bajo el contexto de Spring.
- [ ] **Pruebas de Seguridad:** Verificar mediante `Spring Security Test` que las restricciones de roles (`ROLE_ADMIN` / `ROLE_USER`) y la protección CSRF bloqueen o permitan el acceso correctamente según el endpoint.

### 🏗️ FASE 2: DevOps e Infraestructura
- [ ] **Contenedorización (Docker):** Crear un entorno portable e independiente mediante un archivo `Dockerfile` y `docker-compose` para levantar la aplicación y la base de datos MySQL con un solo comando.
- [ ] **Evolución de Base de Datos (Flyway):** Incorporar un sistema de migraciones para automatizar el versionado de los scripts SQL de la base de datos de manera profesional.
