package ar.edu.unahur.obj2.parcial2.streamHubTests.recomendacionesTests;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import ar.edu.unahur.obj2.parcial2.catalogo.Album;
import ar.edu.unahur.obj2.parcial2.catalogo.AudioLibro;
import ar.edu.unahur.obj2.parcial2.catalogo.Cancion;
import ar.edu.unahur.obj2.parcial2.catalogo.IElementoDelCatalogo;
import ar.edu.unahur.obj2.parcial2.planes.PlanBase;
import ar.edu.unahur.obj2.parcial2.recomendaciones.MotorRecomendaciones;
import ar.edu.unahur.obj2.parcial2.recomendaciones.RecomendacionAleatoria;
import ar.edu.unahur.obj2.parcial2.recomendaciones.RecomendacionColaborativa;
import ar.edu.unahur.obj2.parcial2.recomendaciones.RecomendacionPorGenero;
import ar.edu.unahur.obj2.parcial2.recomendaciones.RecomendacionPorHistorial;
import ar.edu.unahur.obj2.parcial2.usuario.Usuario;

public class RecomendacionesTests {

    private Cancion crearCancion(Integer id, String titulo, String artista, String genero, Integer reproducciones) {
        return new Cancion(id, titulo, 200, artista, genero, false, reproducciones);
    }

    private Usuario crearUsuario(String nombre) {
        return new Usuario(nombre, new PlanBase());
    }

    @Test
    @DisplayName("Dado un motor por genero cuando se cambia a por historial entonces recomienda segun el nuevo algoritmo")
    void dadoUnMotorPorGenero_CuandoSeCambiaAPorHistorial_EntoncesRecomiendaSegunElNuevoAlgoritmo() {
        Cancion deMusica = crearCancion(1, "De musica ligera", "Soda Stereo", "rock", 100);
        Cancion jijiji = crearCancion(2, "Jijiji", "Los Redondos", "rock", 100);
        Cancion cuandoPase = crearCancion(3, "Cuando pase el temblor", "Soda Stereo", "pop", 100);
        List<IElementoDelCatalogo> catalogo = List.of(deMusica, jijiji, cuandoPase);
        Usuario usuario = crearUsuario("Facu");
        usuario.agregarAlHistorial(deMusica);
        MotorRecomendaciones motor = new MotorRecomendaciones(new RecomendacionPorGenero());

        List<Cancion> porGenero = motor.recomendar(catalogo, usuario, List.of());
        motor.setAlgoritmo(new RecomendacionPorHistorial());
        List<Cancion> porHistorial = motor.recomendar(catalogo, usuario, List.of());

        Assertions.assertEquals(List.of(jijiji), porGenero);
        Assertions.assertEquals(List.of(cuandoPase), porHistorial);
    }

    @Test
    @DisplayName("Dado un usuario que escucho rock cuando se recomienda por genero entonces devuelve rock no escuchado en orden del catalogo")
    void dadoUnUsuarioQueEscuchaRock_CuandoSeRecomiendaPorGenero_EntoncesDevuelveRockNoEscuchado() {
        Cancion deMusica = crearCancion(1, "De musica ligera", "Soda Stereo", "rock", 0);
        Cancion gasolina = crearCancion(2, "Gasolina", "Daddy Yankee", "reggaeton", 0);
        Cancion jijiji = crearCancion(3, "Jijiji", "Los Redondos", "rock", 0);
        Cancion rasguna = crearCancion(4, "Rasguña las piedras", "Sui Generis", "rock", 0);
        List<IElementoDelCatalogo> catalogo = List.of(deMusica, gasolina, jijiji, rasguna);
        Usuario usuario = crearUsuario("Facu");
        usuario.agregarAlHistorial(deMusica);

        List<Cancion> resultado = new RecomendacionPorGenero().recomendar(catalogo, usuario, List.of());

        Assertions.assertEquals(List.of(jijiji, rasguna), resultado);
    }

    @Test
    @DisplayName("Dado un catalogo con un album cuando se recomienda por genero entonces busca las canciones dentro del album")
    void dadoUnCatalogoConUnAlbum_CuandoSeRecomiendaPorGenero_EntoncesBuscaDentroDelAlbum() {
        Cancion deMusica = crearCancion(1, "De musica ligera", "Soda Stereo", "rock", 0);
        Cancion persiana = crearCancion(2, "Persiana americana", "Soda Stereo", "rock", 0);
        Album signos = new Album(10, "Signos", "Soda Stereo");
        signos.agregarCancion(persiana);
        List<IElementoDelCatalogo> catalogo = List.of(deMusica, signos);
        Usuario usuario = crearUsuario("Facu");
        usuario.agregarAlHistorial(deMusica);

        List<Cancion> resultado = new RecomendacionPorGenero().recomendar(catalogo, usuario, List.of());

        Assertions.assertEquals(List.of(persiana), resultado);
    }

    @Test
    @DisplayName("Dado un usuario sin historial cuando se recomienda por genero entonces devuelve una lista vacia")
    void dadoUnUsuarioSinHistorial_CuandoSeRecomiendaPorGenero_EntoncesDevuelveListaVacia() {
        List<IElementoDelCatalogo> catalogo = List.of(crearCancion(1, "Jijiji", "Los Redondos", "rock", 0));
        Usuario usuario = crearUsuario("Facu");

        List<Cancion> resultado = new RecomendacionPorGenero().recomendar(catalogo, usuario, List.of());

        Assertions.assertTrue(resultado.isEmpty());
    }

    @Test
    @DisplayName("Dado un usuario que solo escucho un audiolibro cuando se recomienda por genero entonces devuelve una lista vacia")
    void dadoUnUsuarioQueSoloEscuchaUnAudiolibro_CuandoSeRecomiendaPorGenero_EntoncesDevuelveListaVacia() {
        AudioLibro rayuela = new AudioLibro(1, "Rayuela", 3600, "Cortazar", true);
        List<IElementoDelCatalogo> catalogo = List.of(crearCancion(2, "Jijiji", "Los Redondos", "rock", 0));
        Usuario usuario = crearUsuario("Facu");
        usuario.agregarAlHistorial(rayuela);

        List<Cancion> resultado = new RecomendacionPorGenero().recomendar(catalogo, usuario, List.of());

        Assertions.assertTrue(resultado.isEmpty());
    }

    @Test
    @DisplayName("Dado un catalogo con quince canciones del mismo genero cuando se recomienda por genero entonces devuelve diez")
    void dadoUnCatalogoConQuinceCancionesDelMismoGenero_CuandoSeRecomiendaPorGenero_EntoncesDevuelveDiez() {
        List<IElementoDelCatalogo> catalogo = new ArrayList<>();
        for (int i = 1; i <= 15; i++) {
            catalogo.add(crearCancion(i, "Tema " + i, "Artista " + i, "rock", 0));
        }
        Usuario usuario = crearUsuario("Facu");
        usuario.agregarAlHistorial((Cancion) catalogo.get(0));

        List<Cancion> resultado = new RecomendacionPorGenero().recomendar(catalogo, usuario, List.of());

        Assertions.assertEquals(10, resultado.size());
    }

    @Test
    @DisplayName("Dado un usuario que escucho un artista cuando se recomienda por historial entonces devuelve sus canciones de mas a menos reproducidas")
    void dadoUnUsuarioQueEscuchaUnArtista_CuandoSeRecomiendaPorHistorial_EntoncesOrdenaPorReproducciones() {
        Cancion deMusica = crearCancion(1, "De musica ligera", "Soda Stereo", "rock", 100);
        Cancion persiana = crearCancion(2, "Persiana americana", "Soda Stereo", "rock", 300);
        Cancion jijiji = crearCancion(3, "Jijiji", "Los Redondos", "rock", 900);
        Cancion cuandoPase = crearCancion(4, "Cuando pase el temblor", "Soda Stereo", "pop", 500);
        List<IElementoDelCatalogo> catalogo = List.of(deMusica, persiana, jijiji, cuandoPase);
        Usuario usuario = crearUsuario("Facu");
        usuario.agregarAlHistorial(deMusica);

        List<Cancion> resultado = new RecomendacionPorHistorial().recomendar(catalogo, usuario, List.of());

        Assertions.assertEquals(List.of(cuandoPase, persiana), resultado);
    }

    @Test
    @DisplayName("Dadas dos canciones con las mismas reproducciones cuando se recomienda por historial entonces respeta el orden del catalogo")
    void dadasDosCancionesEmpatadas_CuandoSeRecomiendaPorHistorial_EntoncesRespetaElOrdenDelCatalogo() {
        Cancion deMusica = crearCancion(1, "De musica ligera", "Soda Stereo", "rock", 100);
        Cancion persiana = crearCancion(2, "Persiana americana", "Soda Stereo", "rock", 300);
        Cancion ciudadFuria = crearCancion(3, "En la ciudad de la furia", "Soda Stereo", "rock", 300);
        Cancion cuandoPase = crearCancion(4, "Cuando pase el temblor", "Soda Stereo", "pop", 500);
        List<IElementoDelCatalogo> catalogo = List.of(deMusica, persiana, ciudadFuria, cuandoPase);
        Usuario usuario = crearUsuario("Facu");
        usuario.agregarAlHistorial(deMusica);

        List<Cancion> resultado = new RecomendacionPorHistorial().recomendar(catalogo, usuario, List.of());

        Assertions.assertEquals(List.of(cuandoPase, persiana, ciudadFuria), resultado);
    }

    @Test
    @DisplayName("Dado un usuario sin historial cuando se recomienda por historial entonces devuelve una lista vacia")
    void dadoUnUsuarioSinHistorial_CuandoSeRecomiendaPorHistorial_EntoncesDevuelveListaVacia() {
        List<IElementoDelCatalogo> catalogo = List.of(crearCancion(1, "De musica ligera", "Soda Stereo", "rock", 0));
        Usuario usuario = crearUsuario("Facu");

        List<Cancion> resultado = new RecomendacionPorHistorial().recomendar(catalogo, usuario, List.of());

        Assertions.assertTrue(resultado.isEmpty());
    }

    @Test
    @DisplayName("Dado un catalogo con quince canciones del mismo artista cuando se recomienda por historial entonces devuelve diez")
    void dadoUnCatalogoConQuinceCancionesDelMismoArtista_CuandoSeRecomiendaPorHistorial_EntoncesDevuelveDiez() {
        List<IElementoDelCatalogo> catalogo = new ArrayList<>();
        for (int i = 1; i <= 15; i++) {
            catalogo.add(crearCancion(i, "Tema " + i, "Soda Stereo", "rock", i * 10));
        }
        Usuario usuario = crearUsuario("Facu");
        usuario.agregarAlHistorial((Cancion) catalogo.get(0));

        List<Cancion> resultado = new RecomendacionPorHistorial().recomendar(catalogo, usuario, List.of());

        Assertions.assertEquals(10, resultado.size());
    }

    @Test
    @DisplayName("Dado un catalogo grande cuando se recomienda aleatoriamente entonces devuelve diez canciones distintas del catalogo y no escuchadas")
    void dadoUnCatalogoGrande_CuandoSeRecomiendaAleatoriamente_EntoncesCumpleLasPropiedades() {
        List<IElementoDelCatalogo> catalogo = new ArrayList<>();
        for (int i = 1; i <= 40; i++) {
            catalogo.add(crearCancion(i, "Tema " + i, "Artista " + i, "rock", 0));
        }
        Cancion escuchada = (Cancion) catalogo.get(0);
        Usuario usuario = crearUsuario("Facu");
        usuario.agregarAlHistorial(escuchada);

        List<Cancion> resultado = new RecomendacionAleatoria().recomendar(catalogo, usuario, List.of());

        Assertions.assertEquals(10, resultado.size());
        Assertions.assertEquals(resultado.size(), resultado.stream().distinct().count());
        Assertions.assertTrue(catalogo.containsAll(resultado));
        Assertions.assertFalse(resultado.contains(escuchada));
    }

    @Test
    @DisplayName("Dado un catalogo chico cuando se recomienda aleatoriamente entonces devuelve todas las canciones no escuchadas")
    void dadoUnCatalogoChico_CuandoSeRecomiendaAleatoriamente_EntoncesDevuelveTodasLasNoEscuchadas() {
        Cancion deMusica = crearCancion(1, "De musica ligera", "Soda Stereo", "rock", 0);
        Cancion gasolina = crearCancion(2, "Gasolina", "Daddy Yankee", "reggaeton", 0);
        Cancion thriller = crearCancion(3, "Thriller", "Michael Jackson", "pop", 0);
        List<IElementoDelCatalogo> catalogo = List.of(deMusica, gasolina, thriller);
        Usuario usuario = crearUsuario("Facu");
        usuario.agregarAlHistorial(deMusica);

        List<Cancion> resultado = new RecomendacionAleatoria().recomendar(catalogo, usuario, List.of());

        Assertions.assertEquals(2, resultado.size());
        Assertions.assertTrue(resultado.containsAll(List.of(gasolina, thriller)));
    }

    @Test
    @DisplayName("Dados usuarios similares cuando se recomienda colaborativamente entonces ordena por cantidad de similares que la escucharon")
    void dadosUsuariosSimilares_CuandoSeRecomiendaColaborativamente_EntoncesOrdenaPorCantidadDeSimilares() {
        Cancion deMusica = crearCancion(1, "De musica ligera", "Soda Stereo", "rock", 0);
        Cancion jijiji = crearCancion(2, "Jijiji", "Los Redondos", "rock", 0);
        Cancion gasolina = crearCancion(3, "Gasolina", "Daddy Yankee", "reggaeton", 0);
        Cancion thriller = crearCancion(4, "Thriller", "Michael Jackson", "pop", 0);
        Cancion rasguna = crearCancion(5, "Rasguña las piedras", "Sui Generis", "rock", 0);
        List<IElementoDelCatalogo> catalogo = List.of(deMusica, jijiji, thriller, gasolina, rasguna);

        Usuario usuario = crearUsuario("Facu");
        usuario.agregarAlHistorial(deMusica);
        usuario.agregarAlHistorial(jijiji);

        Usuario juan = crearUsuario("Juan");
        juan.agregarAlHistorial(deMusica);
        juan.agregarAlHistorial(jijiji);
        juan.agregarAlHistorial(gasolina);

        Usuario ana = crearUsuario("Ana");
        ana.agregarAlHistorial(deMusica);
        ana.agregarAlHistorial(jijiji);
        ana.agregarAlHistorial(gasolina);
        ana.agregarAlHistorial(thriller);

        Usuario pedro = crearUsuario("Pedro");
        pedro.agregarAlHistorial(deMusica);
        pedro.agregarAlHistorial(rasguna);

        List<Cancion> resultado = new RecomendacionColaborativa().recomendar(catalogo, usuario, List.of(juan, ana, pedro));

        Assertions.assertEquals(List.of(gasolina, thriller), resultado);
    }

    @Test
    @DisplayName("Dado un usuario que comparte una sola cancion cuando se recomienda colaborativamente entonces no es similar y devuelve lista vacia")
    void dadoUnUsuarioQueComparteUnaSolaCancion_CuandoSeRecomiendaColaborativamente_EntoncesDevuelveListaVacia() {
        Cancion deMusica = crearCancion(1, "De musica ligera", "Soda Stereo", "rock", 0);
        Cancion jijiji = crearCancion(2, "Jijiji", "Los Redondos", "rock", 0);
        Cancion gasolina = crearCancion(3, "Gasolina", "Daddy Yankee", "reggaeton", 0);
        List<IElementoDelCatalogo> catalogo = List.of(deMusica, jijiji, gasolina);

        Usuario usuario = crearUsuario("Facu");
        usuario.agregarAlHistorial(deMusica);
        usuario.agregarAlHistorial(jijiji);

        Usuario juan = crearUsuario("Juan");
        juan.agregarAlHistorial(deMusica);
        juan.agregarAlHistorial(gasolina);

        List<Cancion> resultado = new RecomendacionColaborativa().recomendar(catalogo, usuario, List.of(juan));

        Assertions.assertTrue(resultado.isEmpty());
    }

    @Test
    @DisplayName("Dado un usuario que escucho dos veces la misma cancion cuando se compara con otro entonces cuenta como una sola en comun")
    void dadoUnaCancionRepetidaEnElHistorial_CuandoSeRecomiendaColaborativamente_EntoncesNoCuentaDoble() {
        Cancion deMusica = crearCancion(1, "De musica ligera", "Soda Stereo", "rock", 0);
        Cancion gasolina = crearCancion(2, "Gasolina", "Daddy Yankee", "reggaeton", 0);
        List<IElementoDelCatalogo> catalogo = List.of(deMusica, gasolina);

        Usuario usuario = crearUsuario("Facu");
        usuario.agregarAlHistorial(deMusica);
        usuario.agregarAlHistorial(deMusica);

        Usuario juan = crearUsuario("Juan");
        juan.agregarAlHistorial(deMusica);
        juan.agregarAlHistorial(gasolina);

        List<Cancion> resultado = new RecomendacionColaborativa().recomendar(catalogo, usuario, List.of(juan));

        Assertions.assertTrue(resultado.isEmpty());
    }

    @Test
    @DisplayName("Dado un usuario sin historial cuando se recomienda colaborativamente entonces devuelve una lista vacia")
    void dadoUnUsuarioSinHistorial_CuandoSeRecomiendaColaborativamente_EntoncesDevuelveListaVacia() {
        Cancion deMusica = crearCancion(1, "De musica ligera", "Soda Stereo", "rock", 0);
        Usuario usuario = crearUsuario("Facu");
        Usuario juan = crearUsuario("Juan");
        juan.agregarAlHistorial(deMusica);

        List<Cancion> resultado = new RecomendacionColaborativa().recomendar(List.of(deMusica), usuario, List.of(juan));

        Assertions.assertTrue(resultado.isEmpty());
    }

    @Test
    @DisplayName("Dado que el propio usuario esta en la lista de otros usuarios cuando se recomienda colaborativamente entonces no se cuenta a si mismo")
    void dadoQueElUsuarioEstaEnLaLista_CuandoSeRecomiendaColaborativamente_EntoncesNoSeCuentaASiMismo() {
        Cancion deMusica = crearCancion(1, "De musica ligera", "Soda Stereo", "rock", 0);
        Cancion jijiji = crearCancion(2, "Jijiji", "Los Redondos", "rock", 0);
        Usuario usuario = crearUsuario("Facu");
        usuario.agregarAlHistorial(deMusica);
        usuario.agregarAlHistorial(jijiji);

        List<Cancion> resultado = new RecomendacionColaborativa().recomendar(List.of(deMusica, jijiji), usuario, List.of(usuario));

        Assertions.assertTrue(resultado.isEmpty());
    }

    @Test
    @DisplayName("Dado un usuario similar con quince canciones nuevas cuando se recomienda colaborativamente entonces devuelve diez")
    void dadoUnSimilarConQuinceCancionesNuevas_CuandoSeRecomiendaColaborativamente_EntoncesDevuelveDiez() {
        Cancion deMusica = crearCancion(1, "De musica ligera", "Soda Stereo", "rock", 0);
        Cancion jijiji = crearCancion(2, "Jijiji", "Los Redondos", "rock", 0);
        List<IElementoDelCatalogo> catalogo = new ArrayList<>(List.of(deMusica, jijiji));

        Usuario usuario = crearUsuario("Facu");
        usuario.agregarAlHistorial(deMusica);
        usuario.agregarAlHistorial(jijiji);

        Usuario juan = crearUsuario("Juan");
        juan.agregarAlHistorial(deMusica);
        juan.agregarAlHistorial(jijiji);
        for (int i = 3; i <= 17; i++) {
            Cancion nueva = crearCancion(i, "Tema " + i, "Artista " + i, "rock", 0);
            catalogo.add(nueva);
            juan.agregarAlHistorial(nueva);
        }

        List<Cancion> resultado = new RecomendacionColaborativa().recomendar(catalogo, usuario, List.of(juan));

        Assertions.assertEquals(10, resultado.size());
    }
}
