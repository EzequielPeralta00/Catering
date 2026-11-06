# 🍽️ Catering «Mesa Servida» — 2do Parcial

**Materia:** Programación Orientada a Objetos 2 — UNAHUR
**Modalidad:** parcial con resolución TDD (Baby-Steps) y refactors sucesivos
**Duración:** 2 horas
**Lenguaje:** Java 21 + JUnit 5

---

## 🎯 Objetivo

Construir, etapa por etapa, el sistema de armado de propuestas y gestión de la carta de una empresa de catering para eventos.

El enunciado **no indica qué patrones usar**. Cada etapa agrega requerimientos que ponen a prueba el diseño de la anterior. Cuando el código empiece a doler (duplicación, `if` por tipo, explosión de clases), ese es el momento de refactorizar. Identificar **qué problema aparece** y **qué estructura lo resuelve** es parte de lo evaluado.

> ⚠️ Aplicar cualquier estructura que no responda a una necesidad real del enunciado se considera **uso forzado** y descalifica ese punto.

---

## 📋 Criterios de evaluación

- El código debe **compilar** y los tests deben **pasar**.
- Los **nombres de los tests** deben ser descriptivos (`dadoX_alY_Z`).
- Elegir el tipo de cada excepción (verificada o no verificada) aplicando el criterio visto en la cursada y **justificar la elección** en un comentario breve en el código.
- La cobertura debe ser **≥ 75 %**.
- Cada decisión de diseño debe responder a una necesidad del enunciado.
- Se aplica análisis estático anticopia.

### Ponderación

| Etapa | Puntos |
|---|---|
| Etapa 1 — Servicios y menús | 30 |
| Etapa 2 — Adicionales y contratos | 30 |
| Etapa 3 — Gestión de la carta | 30 |
| Calidad general (tests, excepciones, cobertura, ausencia de usos forzados) | 10 |

### Tiempos y checkpoints de push

| Checkpoint | Tiempo orientativo | Qué debe estar en el repositorio |
|---|---|---|
| **CP-1** | ≈ 0:40 · al terminar la Etapa 1 | Catálogo cargado, menús anidados y tests en verde |
| **CP-2** | ≈ 1:20 · al terminar la Etapa 2 | Código de las Etapas 1 y 2 refactorizado |
| **CP-3** | 2:00 · al finalizar | Entrega final |

Distribución sugerida: Etapa 1 ≈ 35 min · Etapa 2 ≈ 35 min · Etapa 3 ≈ 40 min · revisión final ≈ 10 min.

---

## 🗺️ Contexto

«Mesa Servida» arma propuestas para eventos. Una propuesta puede ser un **servicio** suelto (una mesa de entradas, una parrillada, un DJ) o un **menú** que reúne varias propuestas bajo un nombre comercial y con un descuento propio.

### Catálogo inicial

**Servicios**

| Nombre | Precio |
|---|---|
| Mesa de entradas | $80.000 |
| Parrillada completa | $240.000 |
| Mesa dulce | $60.000 |
| Barra de tragos | $150.000 |
| DJ y sonido | $120.000 |
| Brindis de bienvenida | $50.000 |

**Menús**

| Menú | Descuento | Contiene |
|---|---|---|
| Menú Parrillero | 10 % | Mesa de entradas, Parrillada completa, Mesa dulce |
| Pack Fiesta | 5 % | Barra de tragos, DJ y sonido |
| Evento Completo | 20 % | Menú Parrillero, Pack Fiesta, Brindis de bienvenida |

> ℹ️ El servicio *Fotógrafo* ($100.000) no integra el catálogo inicial: se crea en los escenarios de la Etapa 3.

---

## 🧩 Etapa 1 — Servicios y menús

### Parte A — Menús con servicios

**Requerimientos**

1. Modelar los servicios con su nombre y su precio.
2. Modelar los menús con su nombre y su descuento (porcentaje entre 0 y 100).
3. Calcular el **precio** de un menú: suma de los precios de lo que contiene, menos el descuento del menú.
4. Obtener la **descripción** de un servicio o menú (su nombre).
5. Informar la **cantidad de servicios** que ofrece una propuesta (un servicio suelto cuenta como 1).
6. Consultar si una propuesta **incluye** un servicio por nombre.

**Restricciones**

- Lanzar `PrecioNegativoException` al crear un servicio con precio menor a 0.
- Lanzar `DescuentoInvalidoException` al crear un menú con descuento menor a 0 o mayor a 100.

**Resultados esperados**

| Consulta | Resultado |
|---|---|
| Precio de *Menú Parrillero* | $342.000 |
| Precio de *Pack Fiesta* | $256.500 |
| Cantidad de servicios de *Menú Parrillero* | 3 |
| *Menú Parrillero* incluye *Parrillada completa* | sí |
| *Menú Parrillero* incluye *DJ y sonido* | no |

### Parte B — Menús dentro de menús

El catálogo incluye *Evento Completo*, que contiene otros menús. Un menú puede contener servicios **u** otros menús, con cualquier nivel de anidamiento.

**Requerimientos**

1. Calcular el precio, la cantidad de servicios y la inclusión por nombre de *Evento Completo* con las **mismas operaciones** de la parte anterior.
2. Quien consulta una propuesta **no debe saber** si tiene delante un servicio suelto o un menú.

**Restricciones de diseño**

- Evitar `instanceof`, `getClass()` y enums o banderas que indiquen el tipo de una propuesta.
- Evitar duplicar la lógica de recorrido entre servicios y menús.

**Resultados esperados**

| Consulta | Resultado |
|---|---|
| Precio de *Evento Completo* | $518.800 |
| Cantidad de servicios de *Evento Completo* | 6 |
| *Evento Completo* incluye *DJ y sonido* | sí |
| *Evento Completo* incluye *Fotógrafo* | no |

> ℹ️ El precio de *Evento Completo* se calcula sobre el precio **ya descontado** de sus menús internos: (342.000 + 256.500 + 50.000) − 20 % = 518.800.

---

## 🧩 Etapa 2 — Adicionales y contratos

Los clientes pueden contratar **adicionales** sobre cualquier propuesta (servicio suelto o menú completo).

| Adicional | Efecto sobre el precio | Efecto sobre la descripción |
|---|---|---|
| Mozos adicionales | + $18.000 | agrega « + Mozos adicionales» |
| Vajilla premium | + $12.000 | agrega « + Vajilla premium» |
| Servicio nocturno | + 20 % del precio acumulado hasta ese momento | agrega « + Servicio nocturno» |

**Requerimientos**

1. Poder contratar un adicional sobre cualquier propuesta, y obtener una propuesta que se consulta **igual que cualquier otra** (precio, descripción, cantidad de servicios, inclusión).
2. Poder combinar adicionales en **cualquier orden** y **repetir** el mismo adicional.
3. Los adicionales **no agregan servicios**: la cantidad de servicios y la inclusión por nombre se mantienen.
4. Un adicional **no modifica** la propuesta original: quien consulta la propuesta sin adicional sigue viendo el mismo precio y la misma descripción.

**Restricciones de diseño**

- Evitar una clase por cada combinación de adicionales con propuestas.
- Evitar `if` o `switch` por tipo de adicional dentro de las propuestas.
- El costo de sumar un cuarto adicional debe ser **una clase nueva**, sin modificar las existentes.

**Resultados esperados**

| Propuesta | Precio | Descripción |
|---|---|---|
| *Menú Parrillero* + Mozos adicionales + Servicio nocturno | $432.000 | `Menú Parrillero + Mozos adicionales + Servicio nocturno` |
| *Menú Parrillero* + Servicio nocturno + Mozos adicionales | $428.400 | `Menú Parrillero + Servicio nocturno + Mozos adicionales` |
| *Brindis de bienvenida* + Vajilla premium + Vajilla premium | $74.000 | `Brindis de bienvenida + Vajilla premium + Vajilla premium` |
| *Mesa dulce* + Servicio nocturno + Servicio nocturno | $86.400 | `Mesa dulce + Servicio nocturno + Servicio nocturno` |
| *Evento Completo* + Vajilla premium | $530.800 | `Evento Completo + Vajilla premium` |

> ℹ️ El orden importa solo para el servicio nocturno: (342.000 + 18.000) × 1,20 = 432.000, pero 342.000 × 1,20 + 18.000 = 428.400.

### Contratos

Un **contrato** registra el nombre del cliente y la propuesta contratada (con o sin adicionales). Al crearlo, **fija el importe** vigente y la descripción en ese momento.

| Dado | Cuando | Entonces |
|---|---|---|
| Contrato de «Lucía» sobre *Menú Parrillero* + Mozos adicionales | consultar su importe | $360.000 |
| Contrato de «Lucía» sobre *Menú Parrillero* + Mozos adicionales | consultar su descripción | `Menú Parrillero + Mozos adicionales` |

> ℹ️ El contrato **no es una propuesta del catálogo**: no se cotiza ni se combina. Modelarlo con alguna de las estructuras anteriores se considera uso forzado.

---

## 🧩 Etapa 3 — Gestión de la carta

El equipo comercial modifica la carta todos los días. Se necesita un **gestor** que centralice esas modificaciones.

**Modificaciones disponibles**

| Modificación | Efecto |
|---|---|
| Agregar una propuesta a un menú | Pasa a ser un componente directo del menú |
| Quitar una propuesta de un menú | Deja de ser un componente directo del menú |
| Actualizar el precio de un servicio | El servicio pasa a tener el nuevo precio |

**Requerimientos**

1. **Aplicar** una modificación de inmediato. Si se aplica correctamente, queda en el **historial**.
2. **Revertir la última modificación aplicada**, repetidamente, en orden inverso al de aplicación. Revertir una actualización de precio devuelve el precio **anterior**; revertir un agregado quita lo agregado; revertir un quitado lo vuelve a agregar.
3. **Aplicar un lote** de modificaciones en una sola operación, en el orden en que se indican. Un lote es **todo o nada**:
   - Si todas se aplican, cada una queda en el historial, en el orden de aplicación.
   - Si alguna falla, se deshacen en orden inverso las que ya se habían aplicado del lote, el catálogo queda como estaba antes del lote, **ninguna** modificación del lote queda en el historial y se propaga la excepción de la que falló.

**Reglas ante errores**

- Quitar una propuesta que no es componente directo del menú: lanzar `PropuestaNoIncluidaException`, sin alterar el menú.
- Actualizar un precio a un valor negativo: lanzar `PrecioNegativoException`.
- Revertir con el historial vacío: lanzar `NadaParaRevertirException`.
- Una modificación que falla **no se registra** en el historial.

**Restricciones de diseño**

- Evitar que el gestor tenga un `if` o `switch` por tipo de modificación.
- Evitar un método distinto en el gestor por cada combinación de «modificación × inmediata / lote».
- El costo de sumar una cuarta modificación debe ser **una clase nueva**, sin tocar el gestor.

**Resultados esperados**

*Secuencia inmediata* partiendo del catálogo inicial. Se crea el servicio *Fotógrafo* ($100.000).

| Paso | Modificación | Precio de *Menú Parrillero* |
|---|---|---|
| 0 | (catálogo inicial) | $342.000 |
| 1 | Agregar *Fotógrafo* a *Menú Parrillero* | $432.000 |
| 2 | Actualizar precio de *Parrillada completa* a $260.000 | $450.000 |
| 3 | Quitar *Mesa dulce* de *Menú Parrillero* | $396.000 |
| 4 | Revertir | $450.000 |
| 5 | Revertir | $432.000 |
| 6 | Revertir | $342.000 |
| 7 | Revertir | `NadaParaRevertirException` |

*Dos actualizaciones del mismo servicio:* partiendo del catálogo inicial, crear dos modificaciones (actualizar *Parrillada completa* a $260.000 y actualizar *Parrillada completa* a $300.000) y recién entonces aplicarlas, en ese orden.

| Paso | Acción | Precio de *Parrillada completa* |
|---|---|---|
| a | Aplicar ambas | $300.000 |
| b | Revertir | $260.000 |
| c | Revertir | $240.000 |

*Lote:* partiendo del catálogo inicial, aplicar como un solo lote las tres modificaciones de los pasos 1 a 3 de la secuencia inmediata.

| Paso | Acción | Resultado |
|---|---|---|
| a | Aplicar el lote | Precio de *Menú Parrillero*: $396.000 |
| b | Revertir | $450.000 |
| c | Revertir dos veces más | $342.000 |
| d | Revertir | `NadaParaRevertirException` |

*Lote con falla:* partiendo del catálogo inicial, aplicar de inmediato «Agregar *Fotógrafo* a *Menú Parrillero*» y luego aplicar un lote con, en este orden: (1) actualizar el precio de *Parrillada completa* a $260.000, (2) quitar *Barra de tragos* de *Menú Parrillero*.

| Paso | Acción | Resultado |
|---|---|---|
| a | Aplicar el lote | `PropuestaNoIncluidaException` (*Barra de tragos* no está en ese menú) |
| b | Consultar precio de *Menú Parrillero* | $432.000 |
| c | Consultar precio de *Parrillada completa* | $240.000 (la actualización del lote se deshizo) |
| d | Revertir | $342.000 (solo se revierte lo aplicado antes del lote) |
| e | Revertir | `NadaParaRevertirException` |

*Contrato y cambios de carta:* partiendo del catálogo inicial, crear un contrato de «Lucía» sobre *Menú Parrillero* y luego actualizar el precio de *Parrillada completa* a $260.000.

| Consulta | Resultado |
|---|---|
| Precio de *Menú Parrillero* | $360.000 |
| Importe del contrato de «Lucía» | $342.000 |

---

## ✅ Tests mínimos (Given-When-Then)

Escribir los tests con nombres descriptivos. Ejemplos de nombres válidos:

- `dadoUnServicioConPrecioNegativo_alCrearlo_lanzaPrecioNegativoException`
- `dadoUnMenuConDescuentoMayorACien_alCrearlo_lanzaDescuentoInvalidoException`
- `dadoUnMenuQueContieneOtrosMenus_alContarServicios_sumaLosDeTodosLosNiveles`
- `dadoUnServicioNocturnoAplicadoAntesQueMozos_alCalcularPrecio_elOrdenAlteraElPrecioFinal`
- `dadoUnContratoCreado_alActualizarElPrecioDeUnServicio_elImporteContratadoNoCambia`
- `dadaUnaActualizacionDePrecio_alRevertirla_devuelveElPrecioAnterior`
- `dadoUnLoteConUnaModificacionInvalida_alAplicarlo_dejaElCatalogoComoEstaba`

**Cobertura obligatoria**

| Etapa | Casos a cubrir |
|---|---|
| 1 | Precio con descuento · cantidad de servicios · inclusión · precio negativo · descuento inválido · precio, cantidad e inclusión con menús anidados |
| 2 | Cada adicional por separado · combinación en ambos órdenes · adicional repetido · adicional sobre menú · la propuesta original no cambia · contrato con importe fijo |
| 3 | Cada modificación aplicada y revertida · secuencia inmediata · dos actualizaciones del mismo servicio · lote · lote con falla · historial vacío · quitar algo no incluido |

---

## 📌 Recomendaciones de resolución

1. Empezar por el test más simple de la Etapa 1 y avanzar de a un paso.
2. Dejar que el código **duplique** o **incomode** antes de refactorizar. El refactor se justifica por un dolor concreto, no por anticipación.
3. Hacer un commit por cada ciclo rojo → verde → refactor.
4. Antes de cada refactor, confirmar que todos los tests están en verde.
5. Hacer push en cada checkpoint, aunque la etapa no esté completa.
