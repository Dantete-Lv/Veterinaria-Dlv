# Patitas Felices — Microservicios

Sistema de gestión veterinaria desarrollado como proyecto académico para la materia **Microservicios y APIs Escalables — Universidad de Palermo, 2026**.

El proyecto evoluciona desde una arquitectura monolítica hacia una arquitectura distribuida basada en microservicios, utilizando Spring Boot, Spring Cloud, Maven y MySQL.

## 1. Tecnologías utilizadas

- Java 21
- Spring Boot 4.1.1
- Spring Cloud 2025.1.3
- Maven (multi-módulo)
- Spring Data JPA
- MySQL
- Spring Cloud Netflix Eureka
- Spring Cloud Config
- Spring Cloud OpenFeign
- Spring Cloud Gateway (WebFlux)
- Resilience4j
- Spring Boot Actuator
- Swagger / OpenAPI

## 2. Arquitectura del proyecto

El sistema utiliza una arquitectura de microservicios con separación de responsabilidades mediante el patrón **Hexagonal Architecture (Ports & Adapters)**.

Cada microservicio representa un contexto delimitado (*Bounded Context*) y administra sus propios datos.

### Estructura general

```text
patitas-felices/
│
├── pom.xml
│
├── ms-dueno/
├── ms-mascota/
├── ms-veterinario/
├── ms-turno/
│
├── config-server/
├── eureka-server/
└── api-gateway/
```

### Arquitectura hexagonal

Los microservicios de negocio organizan sus componentes en tres capas:

```text
com.patitas.<servicio>/
│
├── domain/
│   ├── model/
│   ├── exception/
│   └── port/
│       ├── in/
│       └── out/
│
├── application/
│
└── adapter/
    ├── in/
    │   └── web/
    │       └── dto/
    │
    └── out/
        └── persistence/
```

**Responsabilidades:**

- **Domain:** contiene las entidades, excepciones y contratos (puertos) del dominio.
- **Application:** implementa los casos de uso y coordina las operaciones mediante los puertos.
- **Adapters:** conectan la aplicación con el exterior, mediante controladores REST, DTOs, persistencia y clientes HTTP.

### Dependency Rule

Las dependencias deben apuntar hacia el interior de la arquitectura.

El dominio no debe depender de los adaptadores ni de los frameworks externos. Los adaptadores implementan los contratos definidos por los puertos.

### Por qué el controller usa la interfaz y no la implementación

`DuenioController` (y el resto de los controllers) depende únicamente de la interfaz `DuenioService`, que es el puerto de entrada del dominio, y no de `DuenioServiceImpl`.

Esto respeta la Dependency Rule: los adaptadores conocen los puertos del dominio, nunca las implementaciones de la capa de aplicación. De esta forma la lógica de negocio puede cambiarse o testearse sin tocar el adaptador web, y el controller trabaja solo con los DTO (`DuenioRequest` y `DuenioResponse`) y con la interfaz del servicio.

## 3. Microservicios y responsabilidades

| Módulo | Puerto | Responsabilidad |
|---|---:|---|
| ms-dueno | 8081 | Gestión de dueños |
| ms-mascota | 8082 | Gestión de mascotas |
| ms-veterinario | 8083 | Gestión de veterinarios |
| ms-turno | 8084 | Gestión de turnos |
| api-gateway | 8080 | Punto de entrada y enrutamiento |
| eureka-server | 8761 | Registro y descubrimiento de servicios |
| config-server | 8888 | Configuración centralizada |

## 4. Bases de datos

Cada microservicio de negocio administra su propia base de datos MySQL.

| Microservicio | Base de datos |
|---|---|
| ms-dueno | dueno_db |
| ms-mascota | mascota_db |
| ms-veterinario | veterinario_db |
| ms-turno | turno_db |

Las relaciones entre microservicios se representan mediante identificadores (`Long`), evitando relaciones JPA directas entre bases de datos independientes.

## 5. Configuración centralizada

El proyecto utiliza Spring Cloud Config Server para obtener la configuración desde el repositorio Git:

**Repositorio:** https://github.com/Dantete-Lv/patitas-config

Archivos de configuración:

```text
patitas-config/
├── application.yml
├── api-gateway.yml
├── ms-dueno.yml
├── ms-mascota.yml
├── ms-veterinario.yml
└── ms-turno.yml
```

Los archivos individuales contienen la configuración específica de cada servicio, mientras que `application.yml` contiene propiedades compartidas (Eureka y Actuator). En cada módulo, el `application.yml` local solo define el nombre, el puerto y la importación del Config Server.

Las credenciales de MySQL se leen de variables de entorno:

- `DB_PASSWORD`: obligatoria (no tiene valor por defecto).
- `DB_USERNAME`: opcional (por defecto `root`).

## 6. Requisitos previos

Antes de ejecutar el proyecto se necesita:

- JDK 21.
- MySQL Server, con las cuatro bases de datos creadas (`dueno_db`, `mascota_db`, `veterinario_db`, `turno_db`).
- Git.
- Acceso a internet: el Config Server clona el repositorio `patitas-config` al arrancar.
- La variable de entorno `DB_PASSWORD` definida. En PowerShell, para la sesión actual:

```powershell
$env:DB_PASSWORD = "tu_password"
```

## 7. Instalación y ejecución

Clonar el repositorio:

```bash
git clone https://github.com/Dantete-Lv/Veterinaria-Dlv.git
cd "Veterinaria-Dlv/Clase 1- Introducción/vet-system/MicroServices/patitas-felices"
```

Compilar todos los módulos:

```powershell
.\mvnw.cmd clean package -DskipTests
```

### Orden de arranque

Para garantizar que los servicios puedan obtener su configuración y registrarse correctamente, se debe respetar el siguiente orden:

**1. Config Server**

```powershell
.\mvnw.cmd spring-boot:run -pl config-server
```

Puerto: `8888`

**2. Eureka Server**

```powershell
.\mvnw.cmd spring-boot:run -pl eureka-server
```

Puerto: `8761`

**3. Microservicios de negocio** (en cualquier orden)

```powershell
.\mvnw.cmd spring-boot:run -pl ms-dueno
.\mvnw.cmd spring-boot:run -pl ms-mascota
.\mvnw.cmd spring-boot:run -pl ms-veterinario
.\mvnw.cmd spring-boot:run -pl ms-turno
```

**4. API Gateway**

```powershell
.\mvnw.cmd spring-boot:run -pl api-gateway
```

Puerto: `8080`

Cada servicio debe ejecutarse en su propio proceso o terminal. Todo cliente externo debe entrar por el gateway (`http://localhost:8080`) y no por los puertos internos 8081 a 8084.

## 8. API Gateway

El gateway enruta las peticiones a los microservicios a través de Eureka (`lb://`):

| Ruta externa | Microservicio destino |
|---|---|
| `/api/duenos/**` | ms-dueno |
| `/api/mascotas/**` | ms-mascota |
| `/api/veterinarios/**` | ms-veterinario |
| `/api/turnos/**` | ms-turno |

Además tiene CORS configurado y un filtro global de logging (`LoggingFilter`) que registra cada petición y su duración. Las rutas activas se pueden consultar en:

```text
http://localhost:8080/actuator/gateway/routes
```

## 9. Monitoreo y documentación de API

### Eureka Dashboard

Permite consultar los servicios registrados:

http://localhost:8761

### Config Server

Permite consultar la configuración centralizada:

```text
http://localhost:8888/ms-dueno/default
http://localhost:8888/ms-mascota/default
http://localhost:8888/ms-veterinario/default
http://localhost:8888/ms-turno/default
http://localhost:8888/api-gateway/default
```

### Spring Boot Actuator

Los microservicios exponen endpoints de monitoreo, según su configuración.

```text
http://localhost:8081/actuator/health
http://localhost:8081/actuator/info
http://localhost:8081/actuator/metrics
```

En `ms-dueno` también están habilitados `env`, `beans` y `mappings`. En `ms-turno` se exponen `circuitbreakers` y `circuitbreakerevents` (ver sección 11).

### Swagger / OpenAPI

Documentación interactiva de las API:

```text
http://localhost:8081/swagger-ui/index.html
http://localhost:8082/swagger-ui/index.html
http://localhost:8083/swagger-ui/index.html
http://localhost:8084/swagger-ui/index.html
```

## 10. Comunicación entre microservicios

El sistema utiliza Spring Cloud OpenFeign para realizar llamadas HTTP entre servicios.

El microservicio `ms-turno` consulta a `ms-mascota` y a `ms-veterinario` para validar que existan antes de registrar un turno. La resolución de servicios se realiza por nombre mediante Eureka, sin direcciones ni puertos fijos.

Dentro de `ms-turno` se respeta la arquitectura hexagonal: el dominio usa los puertos de salida `MascotaServicePort` y `VeterinarioServicePort`, que implementan los adaptadores `MascotaFeignAdapter` y `VeterinarioFeignAdapter`. Cada servicio define sus propios DTO de consumo, sin compartir clases de dominio.

Se configuraron timeouts (`connectTimeout: 3000` y `readTimeout: 5000`).

## 11. Tolerancia a fallos

El módulo `ms-turno` incorpora Resilience4j en los adaptadores de salida (Feign), con dos instancias, `mascotaService` y `veterinarioService`:

| Patrón | Configuración |
|---|---|
| Circuit Breaker | ventana de 10 llamadas, mínimo 5, umbral de fallos 50%, 10 s en OPEN, 3 llamadas de prueba en HALF_OPEN |
| Retry | 3 intentos, espera de 1 s con backoff exponencial (multiplicador 2) |
| Fallback | lanza `ServicioNoDisponibleException`, que el `GlobalExceptionHandler` convierte en HTTP 503 |

Estado de los circuitos y su historial:

```text
http://localhost:8084/actuator/health
http://localhost:8084/actuator/circuitbreakerevents/mascotaService
```

### Escenarios verificados

1. **Circuito CLOSED:** con todos los servicios arriba, los turnos se crean con normalidad.
2. **Circuito OPEN:** con `ms-mascota` apagado, las peticiones responden 503 con el mensaje de fallback y el circuito pasa a `OPEN`.
3. **Recuperación:** al levantar de nuevo `ms-mascota`, el circuito pasa por `HALF_OPEN` y vuelve a `CLOSED`.

## 12. Cómo probar el sistema

Todas las peticiones se hacen contra el gateway (`http://localhost:8080`):

```text
POST /api/duenos
{"nombre":"Carlos","apellido":"Ruiz","dni":"12345678","email":"carlos@mail.com","telefono":"1122334455"}

POST /api/mascotas
{"nombre":"Luna","especie":"Gato","raza":"Siamés","fechaNacimiento":"2023-01-10","duenioId":1}

POST /api/veterinarios
{"nombre":"Marta","apellido":"López","matricula":"MP123","especialidad":"Felinos","email":"marta@patitas.com"}

POST /api/turnos
{"fechaHora":"2026-11-25T10:00:00","motivo":"Control anual","mascotaId":1,"veterinarioId":1}
```

Respuestas esperadas: `201` al crear; `404` si la mascota o el veterinario no existen; `409` si el email (o DNI, matrícula) ya existe; `503` si un servicio remoto no está disponible.

## 13. Decisiones y diferencias respecto de los sprints

- **DNI en ms-dueno:** se agregó el campo `dni` (único y obligatorio) como extensión intencional del modelo del Sprint 9. Los demás campos coinciden con el enunciado (`id`, `nombre`, `apellido`, `email`, `telefono`).
- **Rate Limiter (Sprint 13, Paso 6):** no se activó en el gateway. El filtro `RequestRateLimiter`, tal como figura en el enunciado, requiere además un `KeyResolver` que el sprint no define, y con el filtro activo no se podían crear turnos a través del gateway. La dependencia de Redis (`spring-boot-starter-data-redis-reactive`) queda en el `pom.xml` del gateway.
- **Retry junto al Circuit Breaker:** como el enunciado indica `@CircuitBreaker` y `@Retry` con el mismo fallback, aun con el circuito abierto cada respuesta tarda unos 3 segundos (espera de 1 s y 2 s entre reintentos) antes de devolver el 503.
- **Compatibilidad con Spring Boot 4:** se usa `resilience4j-spring-boot4` y `spring-boot-starter-aspectj` (en lugar de `resilience4j-spring-boot3` y `spring-boot-starter-aop`), y las rutas del gateway van bajo `spring.cloud.gateway.server.webflux`.
- **Config Server con Git:** la configuración se lee del repositorio `patitas-config` en GitHub, como indica el Sprint 10.

## 14. Estado del proyecto

Proyecto académico desarrollado incrementalmente mediante sprints. La implementación llega **hasta el Sprint 13** e incluye arquitectura hexagonal, separación de microservicios, descubrimiento de servicios, configuración centralizada, comunicación con OpenFeign, API Gateway y mecanismos de resiliencia.

El Sprint 14 (autenticación) no forma parte del alcance de este trabajo.

---

**Universidad de Palermo — 2026**  
Microservicios y APIs Escalables  
Proyecto: Patitas Felices
