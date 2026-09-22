## Apéndice — Parcial 1

### Relación Turno–Medicamento

Para relacionar los turnos con los medicamentos se utilizó una relación Many-to-Many. Un turno puede tener asociados varios medicamentos y un mismo medicamento puede ser utilizado en diferentes turnos. La relación se implementó desde la entidad `Turno` mediante una tabla intermedia llamada `turnos_medicamentos`. Se eligió esta alternativa porque representa correctamente la relación del dominio sin duplicar información de los medicamentos. La relación se mantiene de forma unidireccional desde `Turno` hacia `Medicamento`, evitando agregar una colección innecesaria en la entidad `Medicamento`.

### Validación de stock

La asociación de un medicamento a un turno se realiza desde la capa de servicio. Antes de realizar la asociación se verifica que el medicamento tenga stock disponible. Si el stock es cero, se lanza una excepción específica y se devuelve HTTP 422 mediante el manejador global de excepciones. Cuando la asociación es válida, se descuenta una unidad del stock del medicamento. De esta forma se evita que el stock pueda quedar en valores negativos. La operación se realiza dentro de una transacción para mantener consistencia entre la asociación y la actualización del stock.

### Solapamiento

Al crear un turno se verifica que el veterinario no tenga otro turno registrado para la misma fecha y hora. Para realizar esta validación se consulta el repositorio buscando un turno que coincida con el veterinario, la fecha y la hora solicitadas. Si existe un conflicto, se lanza `TurnoSuperpuestoException` desde la capa de servicio. La respuesta HTTP es 409 Conflict y contiene información sobre el turno que genera el conflicto. Además del mensaje descriptivo, se informa el ID, la fecha y la hora del turno conflictivo.

### Cupo de mascotas

El sistema valida que un dueño no supere el límite de cinco mascotas activas al registrar una nueva mascota. Esta validación se realiza en la capa de servicio antes de guardar la nueva mascota. Para determinar la cantidad actual se utiliza una consulta de conteo en `MascotaRepository`. Si el dueño ya posee cinco mascotas, se lanza una excepción específica y se devuelve HTTP 422. De esta manera la regla de negocio queda centralizada en el servicio y no depende del controller.

### Decisión más difícil

La decisión que presentó mayor dificultad fue implementar la relación entre turnos y medicamentos junto con la validación del stock. Fue necesario coordinar la relación JPA, los DTOs, el mapper, el repositorio y la lógica del servicio. También fue importante mantener la responsabilidad de las reglas de negocio dentro de la capa de servicio. Otra parte que requirió especial atención fue modificar la validación de solapamiento para obtener el turno conflictivo en lugar de solamente comprobar si existía. Finalmente, se actualizaron los tests afectados por el cambio para verificar que el comportamiento anterior siguiera funcionando correctamente.