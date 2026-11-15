#  Patitas Felices — Microservicios

Sistema de gestión veterinaria desarrollado como proyecto académico para la materia **Microservicios y APIs Escalables — Universidad de Palermo, 2026**.

El proyecto evoluciona desde una arquitectura monolítica hacia una arquitectura distribuida basada en microservicios, utilizando Spring Boot, Spring Cloud, Maven y MySQL.

## 1. Tecnologías utilizadas

- Java 21
- Spring Boot 4.1.1
- Spring Cloud 2025.1.3
- Maven
- Spring Data JPA
- MySQL
- Spring Cloud Netflix Eureka
- Spring Cloud Config
- Spring Cloud OpenFeign
- Spring Cloud Gateway
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
├── ms-dueno.yml
├── ms-mascota.yml
├── ms-veterinario.yml
└── ms-turno.yml
```

Los archivos individuales contienen la configuración específica de cada microservicio, mientras que `application.yml` contiene propiedades compartidas.

Las credenciales de conexión a MySQL deben configurarse mediante variables de entorno, según las propiedades definidas en cada archivo.

## 6. Requisitos previos

Antes de ejecutar el proyecto se necesita:

- JDK 21.
- MySQL Server.
- Git.
- Acceso al repositorio de configuración `patitas-config`.

También deben existir las cuatro bases de datos correspondientes en MySQL.

## 7. Instalación y ejecución

Clonar el repositorio:

```bash
git clone <https://github.com/Dantete-Lv/Veterinaria-Dlv.git>
cd patitas-felices
```

Compilar todos los módulos:

```powershell
.\mvnw.cmd clean compile
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

**3. Microservicios de negocio**

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

Cada servicio debe ejecutarse en su propio proceso o terminal.

## 8. Monitoreo y documentación de API

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
```

### Spring Boot Actuator

Los microservicios exponen endpoints de monitoreo, según su configuración.

Ejemplo:

```text
http://localhost:8081/actuator/health
http://localhost:8081/actuator/info
http://localhost:8081/actuator/metrics
```

### Swagger / OpenAPI

Documentación interactiva de las API:

```text
http://localhost:8081/swagger-ui/index.html
http://localhost:8082/swagger-ui/index.html
http://localhost:8083/swagger-ui/index.html
http://localhost:8084/swagger-ui/index.html
```

## 9. Comunicación entre microservicios

El sistema utiliza Spring Cloud OpenFeign para realizar llamadas HTTP entre servicios.

El microservicio `ms-turno` consulta los servicios de mascotas y veterinarios para validar las referencias antes de registrar turnos.

La resolución de servicios se realiza mediante Eureka.

## 10. Tolerancia a fallos

El módulo `ms-turno` incorpora Resilience4j para implementar:

- Circuit Breaker.
- Retry.
- Métodos fallback.
- Manejo de indisponibilidad de servicios externos.

Los Circuit Breakers permiten controlar las llamadas a servicios remotos y evitar que los fallos se propaguen innecesariamente.

## 11. Estado del proyecto

Proyecto académico desarrollado incrementalmente mediante sprints.

La implementación contempla arquitectura hexagonal, separación de microservicios, descubrimiento de servicios, configuración centralizada, API Gateway y mecanismos de resiliencia.

---

**Universidad de Palermo — 2026**  
Microservicios y APIs Escalables  
Proyecto: Patitas Felices