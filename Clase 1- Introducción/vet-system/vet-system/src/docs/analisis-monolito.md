# Análisis del monolito - Clínica Veterinaria Patitas Felices

## 1. ¿Qué pasa si solo queremos escalar el módulo de Turnos porque hay un pico de demanda?

En el monolito actual, los módulos de Dueños, Mascotas, Veterinarios y Turnos forman parte de la misma aplicación. Por lo tanto, si aumenta mucho la demanda de Turnos, no podemos escalar únicamente ese módulo de manera independiente. Tendríamos que aumentar los recursos de toda la aplicación, aunque el resto de los módulos no necesite más capacidad.

Con una arquitectura de microservicios, el módulo de Turnos podría convertirse en un servicio independiente y escalarse de forma individual cuando exista un aumento de demanda.

---

## 2. ¿Qué pasa si el módulo de Veterinarios falla por un bug? ¿Afecta a los demás?

En el monolito, los distintos módulos se encuentran dentro de la misma aplicación. Un error grave en el módulo de Veterinarios podría afectar el funcionamiento general de la aplicación y dificultar el acceso a otros módulos.

En una arquitectura de microservicios, Veterinarios podría funcionar como un servicio independiente. De esta manera, un problema en ese servicio tendría un alcance más limitado y los demás servicios podrían continuar funcionando, dependiendo de las comunicaciones que mantengan entre ellos.

---

## 3. ¿Qué pasa si queremos usar MongoDB para el historial de turnos y MySQL para los dueños?

En el monolito actual, la aplicación utiliza una misma tecnología de persistencia para los distintos módulos. Utilizar diferentes tecnologías de base de datos dentro de la misma aplicación requeriría una configuración adicional y aumentaría el acoplamiento.

Con microservicios, cada servicio puede administrar su propia persistencia. Por ejemplo, el servicio de Dueños podría utilizar MySQL mientras que el servicio de Turnos podría utilizar MongoDB para almacenar el historial de turnos.

Esto permitiría elegir la tecnología de almacenamiento más adecuada para las necesidades de cada servicio.

---

## 4. ¿Qué pasa si dos equipos trabajan en paralelo sobre el mismo repositorio?

En un monolito, varios equipos pueden necesitar modificar diferentes partes de una misma aplicación y del mismo código. Esto puede generar conflictos de código, dependencias entre cambios y dificultades para coordinar los despliegues.

Con una arquitectura basada en microservicios, cada equipo podría encargarse de un servicio específico. Por ejemplo, un equipo podría trabajar en Dueños y otro en Turnos. Esto permitiría reducir el acoplamiento entre los equipos y facilitar el desarrollo independiente.

---

## 5. ¿Qué pasa si queremos deployar solo una actualización del módulo de Mascotas?

En el monolito, una modificación en Mascotas forma parte de la misma aplicación. Por lo tanto, para publicar esa actualización normalmente habría que desplegar nuevamente la aplicación completa, aunque los demás módulos no hayan cambiado.

Con microservicios, Mascotas podría ser un servicio independiente. Una actualización de ese servicio podría desplegarse sin necesidad de volver a desplegar los servicios de Dueños, Veterinarios o Turnos.