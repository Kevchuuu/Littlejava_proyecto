# 📋 [Ir al README](README.md) 🔍

---

# 🎵 StreamHub: Plataforma de Streaming Musical

## 📘 Contexto

Se nos ha encomendado el diseño del núcleo de software para **StreamHub**, una plataforma de streaming musical encargada de la gestión del catálogo, la reproducción, las recomendaciones y la monetización del contenido. El sistema debe ser flexible y permitir incorporar nuevos tipos de contenido, algoritmos de recomendación y funcionalidades comerciales sin modificar la estructura central del negocio.

<p align="center">
  <img src="./assets/logo.png" alt="StreamHub Streaming Musical" width="850"/>
</p>

---

## 🧭 Consideraciones Generales del Modelo

> [!IMPORTANT]
> Estas pautas aplican a **todo** el modelo.

* **Alcance:** la solución llega hasta las pruebas unitarias. No se reproduce audio real. Todo lo que este enunciado describe como "registrar" o "informar" debe quedar almacenado en el modelo y poder consultarse desde los tests.
* **Cálculos decimales:** los valores no se redondean. En los tests, las comparaciones de decimales deben hacerse con una tolerancia de `0.01`.
* **Excepciones:** todas las excepciones del dominio son **no chequeadas** y heredan de una excepción raíz común (`StreamHubException`). Deben incluir un mensaje descriptivo. Una operación que falla por una excepción de dominio **no debe dejar cambios parciales**.
* **Datos libres:** los identificadores, nombres y valores de ejemplo no indicados en este enunciado quedan a criterio del equipo. Los parámetros operativos, en cambio, deben obtenerse siempre de la configuración centralizada.

---

## 🎵 Estructura del Catálogo

Los elementos del catálogo deben poder tratarse de manera uniforme:

* **Canción:** identificador único, título, artista, duración en segundos, género, indicador de **contenido premium** y contador de reproducciones.
* **Audiolibro:** identificador único, título, autor y duración en segundos. Siempre es premium.
* **Podcast:** identificador único, título, conductor, indicador de contenido premium y una lista de **episodios**, cada uno con título y duración en segundos. Un episodio es premium si su podcast lo es. Los episodios no son elementos del catálogo por sí solos: no pueden agregarse sueltos a un álbum ni a una playlist.
* **Álbum:** identificador único, título y artista, indicados al crearlo. Agrupa canciones de **ese** artista: agregar una canción de otro artista lanza `ArtistaIncompatibleException`.
* **Playlist:** agrupación de contenido de cualquier tipo, incluidas otras playlists. Una playlist **nunca** puede contenerse a sí misma, ni directa ni indirectamente: el intento lanza `PlaylistCiclicaException`.

Se denomina **pista** a cada unidad reproducible: una canción, un audiolibro o un episodio de podcast. Sólo las pistas se reproducen; los álbumes, playlists y podcasts se escuchan encolando sus pistas.

> ⚠️ **Requisito:** cualquier elemento del catálogo debe permitir consultar su **duración total en segundos**, su **cantidad de pistas** y la **lista ordenada de sus pistas**.

Para calcular estas magnitudes:

* Una canción y un audiolibro representan una pista cada uno; un podcast tiene tantas pistas como episodios.
* Álbumes y playlists calculan sus magnitudes recorriendo recursivamente su contenido, respetando el orden en que se agregaron los elementos.
* Un álbum o playlist vacío tiene duración y cantidad de pistas iguales a cero.

### 📐 Ejemplo de referencia

Una playlist contiene una canción de 200 s, un podcast con dos episodios de 600 s y 900 s, y un audiolibro de 3.600 s. Su duración total es **5.300 s** y tiene **4** pistas.

---

## 🧪 Cálculo de Regalías

Antes de liquidar los pagos a artistas y sellos, cualquier elemento del catálogo debe calcular su **costo de regalías** para una cantidad de reproducciones, mediante una fórmula común:

> **Costo de Regalías = Duración Total (seg) × Coeficiente de Regalía × Cantidad de Reproducciones**

El algoritmo es el mismo para todos los elementos; cada tipo define únicamente su coeficiente:

* **Canción:** 7%.
* **Podcast:** 12%.
* **Audiolibro:** 15%.
* **Álbum y Playlist:** el **promedio ponderado por duración** de los coeficientes de su contenido.

Reglas:

* La cantidad de reproducciones indicada para un álbum o playlist se aplica por igual a cada uno de sus contenidos. Por lo tanto, el costo de regalías de un contenedor **coincide** con la suma de los costos de regalías de su contenido.
* Los contenedores vacíos tienen coeficiente y costo de regalías iguales a cero.

📐 Con el ejemplo anterior y 100 reproducciones: canción **\$1.400**, podcast **\$18.000**, audiolibro **\$54.000** y la playlist **\$73.400**.

---

## 🧮 Algoritmos de Recomendación

Cada vez que un usuario abre la aplicación, el **motor de recomendaciones** sugiere una lista de canciones del catálogo. El algoritmo puede cambiarse en tiempo de ejecución (al pasar entre las pestañas "Descubrir", "Continuar escuchando" o "Radio infinita") sin modificar el motor.

Cada usuario tiene un **historial** ordenado de las pistas que reprodujo. El motor recibe el catálogo, el usuario y, cuando corresponde, los demás usuarios de la plataforma. La cantidad máxima de recomendaciones se obtiene de la configuración. Cuando una regla menciona la **última canción del historial**, se refiere a la pista más reciente del historial que sea una canción.

* **Por Género:** canciones del mismo género que la última canción del historial, en el orden del catálogo.
* **Por Historial:** canciones del mismo artista que la última canción del historial, ordenadas de mayor a menor cantidad de reproducciones.
* **Colaborativa:** se consideran **similares** los usuarios que comparten con el usuario al menos 2 canciones en su historial. Se recomiendan las canciones escuchadas por usuarios similares, ordenadas por la cantidad de usuarios similares que las escucharon (de mayor a menor).
* **Aleatoria:** canciones distintas del catálogo, elegidas al azar.

Reglas comunes:

* Nunca se recomienda una canción que ya esté en el historial del usuario, ni una canción repetida.
* Nunca se supera la cantidad máxima de recomendaciones.
* Si no hay datos suficientes (por ejemplo, historial vacío), el resultado es una lista vacía.
* Ante empates de orden, se respeta el orden del catálogo.
* Para la modalidad aleatoria, los tests deben verificar propiedades del resultado (ausencia de duplicados, cantidad máxima, pertenencia al catálogo), sin exigir una selección u orden específicos.

---

## 💳 Planes de Suscripción

La monetización se basa en planes de suscripción **apilables**. Un usuario contrata un plan base y le agrega funcionalidades que modifican su costo mensual y sus capacidades.

La calidad de reproducción es siempre uno de tres niveles, de menor a mayor: `ESTANDAR` (128 kbps), `ALTA` (320 kbps) y `LOSSLESS` (1411 kbps, calidad CD sin pérdida).

| Componente | Costo mensual | Efecto |
| --- | --- | --- |
| **Plan Base** | \$1.000 | Calidad máxima `ESTANDAR`. Con publicidad. Sin descargas. Sin contenido premium. |
| **+ Alta Calidad** | +$300 | Calidad máxima `ALTA`. Habilita contenido premium. |
| **+ Lossless** | +$600 | Calidad máxima `LOSSLESS`. Habilita contenido premium. |
| **+ Descargas Offline** | +$200 | Habilita descargas. |
| **+ Sin Publicidad** | +$500 | Elimina la publicidad. |

Reglas:

* El costo de cada funcionalidad contratada se suma, incluso si se contratan Alta Calidad y Lossless a la vez.
* La calidad máxima efectiva es la más alta entre las funcionalidades contratadas.
* Descargas y publicidad son independientes de la calidad.
* **El orden en que se agregan las funcionalidades no debe alterar el costo ni las capacidades finales.**

📐 Base + Alta Calidad + Lossless + Sin Publicidad: **\$2.400** por mes, calidad máxima **`LOSSLESS`**, con contenido premium, sin publicidad y sin descargas.

---

## ⚙️ Parámetros Globales de Operación

| Parámetro | Valor |
| --- | --- |
| Coeficiente de regalías de canciones / podcasts / audiolibros | **7% / 12% / 15%** |
| Costo mensual del Plan Base | **\$1.000** |
| Costo mensual de Alta Calidad / Lossless / Descargas / Sin Publicidad | **\$300 / \$600 / \$200 / \$500** |
| Bitrate estándar / Alta Calidad / Lossless | **128 / 320 / 1411 kbps** |
| Cantidad máxima de recomendaciones | **10** |
| Canciones en común para considerar usuarios similares | **2** |

Los valores deben obtenerse desde esta configuración centralizada y **no** deben repetirse como literales en las distintas clases del dominio.

La configuración puede implementarse mediante un mecanismo de acceso centralizado. El uso de un Singleton es opcional: implementarlo correctamente no otorga puntos adicionales y no implementarlo no resta puntos.

---

## ⏯️ Consola de Reproducción

Cada usuario tiene un **reproductor** con: la pista actual (si la hay), un estado (`DETENIDO`, `REPRODUCIENDO` o `PAUSADO`), la calidad de reproducción vigente (inicialmente `ESTANDAR`) y una **cola de reproducción** ordenada de pistas.

Las acciones del usuario se **encolan** en la `ConsolaReproduccion` y se ejecutan en lote, en el orden en que fueron encoladas, mediante `ejecutarLote()`.

Acciones contempladas:

* **Play:** comienza a reproducir una pista, que reemplaza a la actual (la pista anterior no vuelve a la cola).
* **Pause:** pausa la reproducción activa. Si no hay reproducción activa, no tiene efecto.
* **Skip:** toma la primera pista de la cola y la reproduce. Si la cola está vacía, se lanza `ColaVaciaException`.
* **AddToQueue:** agrega al final de la cola **todas las pistas** de un elemento del catálogo (por ejemplo, una playlist completa), en orden.
* **CambiarCalidad:** cambia la calidad de reproducción a uno de los tres niveles.

Reglas:

* Una pista premium sólo puede comenzar a reproducirse (por Play o por Skip) si el plan del usuario habilita contenido premium. En caso contrario, se lanza `ContenidoNoDisponibleException` y el reproductor no cambia (si falla un Skip, la pista sigue primera en la cola).
* Se pueden encolar pistas premium con cualquier plan: la validación ocurre recién cuando la pista comienza a reproducirse.
* Cada vez que una pista comienza a reproducirse se agrega al historial del usuario y, si es una canción, se incrementa su contador de reproducciones.
* No se puede cambiar a una calidad superior a la máxima del plan: se lanza `CalidadNoPermitidaException`.

### Ejecución en lote

* Una acción que falle por una excepción de dominio **no impide** que se ejecuten las siguientes.
* La consola debe **registrar una anomalía** por cada acción fallida (qué acción falló y el motivo), consultable luego desde los tests.
* Las acciones fallidas quedan disponibles para su corrección: una acción de *CambiarCalidad* fallida debe poder **corregir la calidad solicitada** y volver a encolarse.
* Al finalizar `ejecutarLote()` la cola de acciones queda vacía.

### Deshacer

La consola debe soportar `deshacerUltimo()`, que revierte la **última acción ejecutada correctamente** que admita deshacer. Las acciones fallidas nunca forman parte de ese historial.

Deben poder deshacerse, como mínimo:

* **AddToQueue:** quita de la cola exactamente las pistas que había agregado esa acción.
* **Skip:** devuelve la pista al principio de la cola, restaura la pista y el estado previos del reproductor, y revierte el registro en el historial y en el contador de reproducciones.

Si no existe ninguna acción ejecutada que admita deshacer, se lanza `SinAccionesParaDeshacerException`.

---

## ⚠️ Manejo de Excepciones de Dominio

Todas heredan de `StreamHubException` y son no chequeadas.

1. **`ContenidoNoDisponibleException`:** al intentar reproducir una pista premium con un plan que no habilita contenido premium. La reproducción no se inicia.
2. **`CalidadNoPermitidaException`:** al cambiar a una calidad que el plan no soporta. La consola debe capturarla, registrar la anomalía y continuar con las demás acciones. Luego de corregir la calidad solicitada y volver a encolar la acción, el reintento se ejecuta con la calidad corregida y respeta las validaciones del plan vigente.
3. **`ColaVaciaException`:** al ejecutar Skip con la cola de reproducción vacía.
4. **`PlaylistCiclicaException`:** al agregar a una playlist un elemento que la contiene (o que es ella misma).
5. **`ArtistaIncompatibleException`:** al agregar a un álbum una canción de otro artista.
6. **`SinAccionesParaDeshacerException`:** al invocar `deshacerUltimo()` sin acciones deshacibles ejecutadas.

---

## 🚫 Fuera de Alcance

* Reproducción de audio real, streaming de red o descargas efectivas de archivos.
* Cobro real de suscripciones o publicidad efectiva.
* Servicios externos de recomendación: los datos de usuarios e historiales se reciben controlados.
* Persistencia, interfaz gráfica o de consola.

---

## 🧪 Casos de Prueba Mínimos Esperados

La suite de pruebas unitarias deberá contemplar obligatoriamente los siguientes escenarios:

* **Magnitudes jerárquicas:** una playlist con canciones, álbumes, podcasts, audiolibros y playlists anidadas devuelve duración total, cantidad de pistas y lista ordenada de pistas.
* **Integridad del catálogo:** `assertThrows` de `PlaylistCiclicaException` (incluido el caso indirecto) y de `ArtistaIncompatibleException`.
* **Regalías:** canciones, podcasts y audiolibros calculan su costo con la fórmula común; un álbum o playlist devuelve la suma de sus contenidos mediante el coeficiente ponderado; un contenedor vacío da cero.
* **Recomendaciones:** el motor cambia de algoritmo en ejecución y cada modalidad devuelve el resultado esperado con datos controlados (respetando exclusiones, orden y cantidad máxima); la aleatoria se verifica por propiedades.
* **Planes de suscripción:** un plan base con Alta Calidad, Lossless y Sin Publicidad produce el costo y las capacidades esperadas, independientemente del orden.
* **Contenido premium:** `assertThrows` de `ContenidoNoDisponibleException` al reproducir una pista premium con plan base, sin que el reproductor cambie.
* **Recuperación de calidad:** el cambio de calidad falla con `CalidadNoPermitidaException`, la consola registra la anomalía y continúa el lote, y tras corregir y reencolar la acción, el reintento se ejecuta con éxito.
* **Cola vacía:** `assertThrows` de `ColaVaciaException` al ejecutar Skip.
* **Ejecución en lote:** las acciones se ejecutan en orden y una acción fallida no impide procesar las siguientes ni entra al historial de deshacer.
* **Deshacer una incorporación:** quita de la cola exactamente las pistas agregadas por la acción.
* **Deshacer un avance:** restaura la cola, la pista y el estado previos del reproductor, el historial y el contador de reproducciones.
* **Deshacer sin historial:** `assertThrows` de `SinAccionesParaDeshacerException`.

---

## 📊 Cobertura Esperada

* La cobertura se mide con **JaCoCo**: mínimo **90% de cobertura global**.
* Además, deben quedar ejercitados todos los caminos (incluidos los que lanzan excepciones) de: las magnitudes, la integridad y las regalías del catálogo, los cuatro algoritmos de recomendación, los planes de suscripción, cada acción de reproducción (ejecución y deshacer) y la `ConsolaReproduccion`.
