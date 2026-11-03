# 2do Parcial de Objetos II - Grupal asincrónico

---

> [!IMPORTANT]
> 👀 **Leer todo el documento en equipo antes de comenzar a debatir y resolver.**

## ⚙️ Datos obligatorios a completar

Registrar en la tabla a cada miembro del equipo. De forma individual cargar y verificar sus datos personales para asegurarse que son correctos.


> **Borrar los datos de ejemplo y poner los datos reales**

| ID | APELLIDO | NOMBRE | DNI |
| --- | ---| --- | --- |
| 1 | SANCHEZ | KEVIN | 43252937 |
| 2 | AGUIAR | DIEGO | 19142606 |
| 3 | SCALESE| LUCIO | 47093489 |
| 4 | SUAREZ | FACUNDO | 42319362 |
| 5 | MACEIRO| MILAGROS | 47233601 |
| 6 | AMBROSETTI | JOAQUIN | 46293343 |


### 📖 Referencias Históricas

> * **Cora Ratto de Sadosky** (1912–1981): Matemática y activista que jugó un rol decisivo en la creación del Instituto de Cálculo y en la formación de las primeras promociones de profesionales de la computación del país.

> * **Osvaldo Fasciolo** (1922–2010): Ingeniero en telecomunicaciones que estuvo a cargo de la puesta en marcha técnica y el mantenimiento inicial de Clementina, asegurando el funcionamiento operativo del equipo informático precursor.

> * **Jorge Sabato** (1924–1983): Físico y tecnólogo argentino que formuló en 1968 el influyente "Triángulo de Sabato", un modelo que planteó la interacción clave entre el Estado, la infraestructura científico-tecnológica y el sector productivo para lograr el desarrollo nacional.

> * **Victoria Bajar** (1942–2023): Fue una de las primeras graduadas de la carrera de Computador Científico de la UBA (década de 1960) y pionera en la investigación y enseñanza de bases de datos y lenguajes de programación en el país y en América Latina.

> * **Alberto Cassacci** (1938–2014): Uno de los primeros graduados y docentes de la carrera de Computador Científico en la UBA (creada en 1962), pionero en la enseñanza de programación y algoritmia universitaria en América Latina.

> * **Elsa Quintana de Moreno**: Programadora e investigadora pionera que integró el equipo inicial del Instituto de Cálculo en la década de 1960, participando en el desarrollo de software científico y modelos matemáticos aplicados a problemas nacionales sobre Clementina.



> [!IMPORTANT]
> * COMPLETAR CÓMO EN EL EJEMPLO, RESPETANDO FORMATO, ESTRUCTURA, MAYÚSCULAS Y ESPACIOS
> * Esta tarea debe ser el commit y push inicial de cada equipo antes de escribir una sola línea de código.

---

## 📝 Consideraciones Iniciales y Criterio de Evaluación

> Se evaluará la solución entregada prestando especial atención a:

* **Pautas obligatorias de Git y flujo de equipo** (descritas abajo) correctamente cumplidas.
* **Entendimiento y correcta aplicación de los conceptos de la materia**: Uso de los patrones de diseño estudiados en clase (Strategy, Template Method, Composite, Decorator y Command), reificación, modularización y manejo de **excepciones de dominio no chequeadas**. Solo se evaluarán los patrones trabajados en clase. El uso de cualquier otro patrón no será tenido en cuenta, a menos que rompa o interfiera en el flujo de un patrón obligatorio.
* **Estrategia de Testing**: El código entregado debe contar con una suite de pruebas unitarias exhaustiva utilizando los enfoques **GWT (Given-When-Then)** / **AAA (Arrange-Act-Assert)** trabajados, garantizando una **cobertura mínima global esperada del 90%**.
* **Prolijidad, diseño y legibilidad**: Se evaluará la cohesión de las clases, el bajo acoplamiento y el uso inadecuado de estructuras de control rígidas (`if/else/switch`) en los casos donde debe haber polimorfismo puro.
* **Análisis de Autoría**: Se realizará un control riguroso del historial de contribuciones mediante herramientas de análisis estático. La presencia de código ajeno/copiado descalifica al grupo automáticamente.

---

## 🛠️ Stack técnico obligatorio

* **Java 21**.
* **Maven** como único gestor de build y dependencias.
* **JUnit 5** para pruebas unitarias.
* **JaCoCo** para medición de cobertura.
* **Estructura de paquetes**: correctamente definida.
* **Prohibido**: frameworks de inyección (Spring, Guice), Lombok, y cualquier
  librería que oculte el diseño de patrones (debe verse el patrón a simple vista).

---

## 📌 Pautas Obligatorias para la Dinámica de Trabajo (Git)

> Para simular un entorno de desarrollo profesional y asegurar la participación equitativa, utilizaremos un sistema de control de flujo basado en los siguientes hitos obligatorios:

* 🛠️ **Compilación absoluta**: El código entregado en la rama principal (`main`) debe compilar obligatoriamente. **Una entrega cuyo código no compile queda desaprobada automáticamente para todo el grupo**. Sin excepciones y sin admisión de reclamos ni explicaciones.
* 👥 **Distribución y Commits**: Las entregas donde todo el código sea subido por un único integrante, o que cuenten con un solo commit masivo al final, serán desaprobadas. Se revisará el historial de Git para verificar que **todos los miembros del equipo hayan aportado de forma equitativa** al repositorio mediante commits claros y periódicos (commits atómicos, mensajes descriptivos e integración progresiva).

> [!IMPORTANT]
> Los siguientes **hitos son puntos de control de estado del repositorio —no commits específicos—**. Se espera que cada equipo realice **tantos commits como sea necesario** para demostrar un flujo de trabajo real (commits atómicos, mensajes claros, integración progresiva). No se debe esperar a un hito para hacer commit, ni limitar la cantidad de commits a uno por hito.

* **Hito 1 (Configuración Inicial)**: Antes de comenzar a codificar el dominio, el repositorio remoto debe reflejar los datos personales de cada integrante y la configuración inicial del proyecto. (Coordinen push y pull para no pisarse y mitigar conflictos.)
* **Hito 2 (Progreso Continuo)**: Durante el desarrollo, el repositorio debe reflejar la integración incremental de las distintas partes del modelo y sus correspondientes pruebas unitarias.
* **Hito 3 (Entrega Final)**: Al cumplirse el plazo estipulado, el repositorio debe contener la solución completa e integrada. El último cambio impactado antes del límite horario será el evaluado.
* Los repositorios se cerrarán automáticamente **en la fecha informada en la plataforma MATE**, dejando deshabilitada la posibilidad de realizar push luego de ese límite.

---

## 🗣️ Defensa Oral Obligatoria

Luego de la entrega del código, el grupo completo se presentará a una **defensa oral presencial o virtual** (según defina la cátedra).

* La nota final será **individual**.
* Se evaluará la capacidad de cada integrante para justificar las decisiones de diseño arquitectónico tomadas por el equipo, explicar el flujo de delegación de responsabilidades de los patrones implementados y proponer adaptaciones en vivo ante nuevos requerimientos del negocio.

> [!WARNING]
> **ESTAS PAUTAS DE TRABAJO Y LOGÍSTICA EN GIT SON OBLIGATORIAS. EL NO CUMPLIMIENTO DE CUALQUIERA DE ELLAS ANULA LA EVALUACIÓN DEL EXAMEN, INDEPENDIENTEMENTE DE QUE LA SOLUCIÓN TÉCNICA ESTÉ PERFECTA.** ‼️

---

# 📝 [Ir al dominio](DOMINIO.md) 🧾

---

## 🧭 Verificación previa a la entrega

Antes del push final, el grupo debe poder responder **[x]** a todas:

- [ ] `maven` compila y pasa los tests.
- [ ] `JaCoCo` reporta ≥ 90% de cobertura global.
- [ ] Hay al menos un test con `assertThrows` por cada excepción definida en `DOMINIO.md`.
- [ ] Todos los tests tienen nombre GWT y estructura AAA.
- [ ] No hay `if/else` ni `switch` decidiendo comportamiento entre tipos del dominio.
- [ ] Cada patrón obligatorio tiene al menos una clase concreta y al menos un test que lo ejercite.
- [ ] Todos los integrantes tienen commits propios y distribuidos.