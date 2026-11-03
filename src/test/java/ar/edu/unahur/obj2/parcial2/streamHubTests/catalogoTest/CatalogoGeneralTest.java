package ar.edu.unahur.obj2.parcial2.streamHubTests.catalogoTest;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ar.edu.unahur.obj2.parcial2.catalogo.Album;
import ar.edu.unahur.obj2.parcial2.catalogo.AudioLibro;
import ar.edu.unahur.obj2.parcial2.catalogo.Cancion;
import ar.edu.unahur.obj2.parcial2.catalogo.Episodio;
import ar.edu.unahur.obj2.parcial2.catalogo.Pista;
import ar.edu.unahur.obj2.parcial2.catalogo.Playlist;
import ar.edu.unahur.obj2.parcial2.catalogo.Podcast;
import ar.edu.unahur.obj2.parcial2.excepciones.ArtistaIncompatibleException;
import ar.edu.unahur.obj2.parcial2.excepciones.PlaylistCiclicaException;
import ar.edu.unahur.obj2.parcial2.planes.Configuracion;

public class CatalogoGeneralTest {

    private Playlist playlistRaiz;
    private Playlist playlistAnidada;
    private Cancion cancion1;
    private Cancion cancionAlbum;
    private AudioLibro audioLibro;
    private Podcast podcast;
    private Episodio episodio;
    private Album album;

    @BeforeEach
    void setUp() {
        cancion1 = new Cancion(1, "De Música Ligera", 200, "Soda Stereo", "Rock", false, 0);
        audioLibro = new AudioLibro(2, "Ficciones", 3000, "Borges", false);

        podcast = new Podcast(3, "La Cruda", "Migue Granados", false);
        episodio = new Episodio(4, "Episodio 1", 1000, false, 1, podcast);
        podcast.agregarEpisodio(episodio);

        cancionAlbum = new Cancion(5, "Persiana Americana", 300, "Soda Stereo", "Rock", false, 0);
        album = new Album(6, "Signos", "Soda Stereo");
        album.agregarCancion(cancionAlbum);

        playlistAnidada = new Playlist(7, "Sub Playlist");
        playlistAnidada.agregarElemento(audioLibro);

        playlistRaiz = new Playlist(8, "Playlist Raíz");
        playlistRaiz.agregarElemento(cancion1);
        playlistRaiz.agregarElemento(album);
        playlistRaiz.agregarElemento(podcast);
        playlistRaiz.agregarElemento(playlistAnidada);
    }

    @Test
    void dadaUnaPlaylistJerarquica_CuandoSeConsultaSuDuracionTotal_EntoncesDevuelveLaSumaExactaDeCuatroMilQuinientos() {
        Assertions.assertEquals(4500, playlistRaiz.getDuracionEnSegundos());
    }

    @Test
    void dadaUnaPlaylistJerarquica_CuandoSeConsultaLaCantidadDePistas_EntoncesDevuelveUnTotalDeCuatro() {
        Assertions.assertEquals(4, playlistRaiz.getCantidadPistas());
    }

    @Test
    void dadaUnaPlaylistJerarquica_CuandoSePidenSusPistas_EntoncesDevuelveLaListaOrdenadaYAplanada() {
        List<Pista> esperadas = List.of(cancion1, cancionAlbum, episodio, audioLibro);
        Assertions.assertEquals(esperadas, playlistRaiz.getPistas());
    }

    @Test
    void dadaUnaPlaylist_CuandoIntentaAgregarseASiMisma_EntoncesLanzaPlaylistCiclicaException() {
        Assertions.assertThrows(PlaylistCiclicaException.class, () -> playlistRaiz.agregarElemento(playlistRaiz));
    }

    @Test
    void dadasPlaylistsAnidadas_CuandoSeIntentaGenerarUnCicloIndirecto_EntoncesLanzaPlaylistCiclicaException() {
        Playlist plA = new Playlist(1, "A");
        Playlist plB = new Playlist(2, "B");
        Playlist plC = new Playlist(3, "C");

        plA.agregarElemento(plB);
        plB.agregarElemento(plC);

        Assertions.assertThrows(PlaylistCiclicaException.class, () -> plC.agregarElemento(plA));
    }

    @Test
    void dadoUnAlbum_CuandoSeIntentaAgregarUnaCancionDeOtroArtista_EntoncesLanzaArtistaIncompatibleException() {
        Cancion cancionDistintoArtista = new Cancion(9, "Ji Ji Ji", 250, "Los Redondos", "Rock", false, 0);
        Assertions.assertThrows(ArtistaIncompatibleException.class, () -> album.agregarCancion(cancionDistintoArtista));
    }

    @Test
    void dadaUnaCancion_CuandoCalculaSuCosto_EntoncesAplicaLaFormulaConSuCoeficiente() {
        Double costoEsperado = 200 * Configuracion.COEFICIENTE_CANCION * 2;
        Assertions.assertEquals(costoEsperado, cancion1.getCostoRegalias(2));
    }

    @Test
    void dadoUnPodcast_CuandoCalculaSuCosto_EntoncesAplicaLaFormulaConSuCoeficiente() {
        Double costoEsperado = 1000 * Configuracion.COEFICIENTE_PODCAST * 1;
        Assertions.assertEquals(costoEsperado, podcast.getCostoRegalias(1));
    }

    @Test
    void dadoUnAudioLibro_CuandoCalculaSuCosto_EntoncesAplicaLaFormulaConSuCoeficiente() {
        Double costoEsperado = 3000 * Configuracion.COEFICIENTE_AUDIOLIBRO * 1;
        Assertions.assertEquals(costoEsperado, audioLibro.getCostoRegalias(1));
    }

    @Test
    void dadoUnContenedor_CuandoCalculaSusRegalias_EntoncesDevuelveLaSumaMedianteCoeficientePonderado() {
        Assertions.assertEquals(6050.0, playlistRaiz.getCostoRegalias(10));
    }

    @Test
    void dadoUnContenedorVacio_CuandoCalculaSusRegalias_EntoncesDevuelveCero() {
        Playlist playlistVacia = new Playlist(99, "Vacía");
        Assertions.assertEquals(0.0, playlistVacia.getCostoRegalias(100));
    }
}