package ar.edu.unahur.obj2.parcial2.streamHubTests.catalogoTest.playlistTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import ar.edu.unahur.obj2.parcial2.catalogo.Album;
import ar.edu.unahur.obj2.parcial2.catalogo.AudioLibro;
import ar.edu.unahur.obj2.parcial2.catalogo.Cancion;
import ar.edu.unahur.obj2.parcial2.catalogo.Episodio;
import ar.edu.unahur.obj2.parcial2.catalogo.Playlist;
import ar.edu.unahur.obj2.parcial2.catalogo.Podcast;
public class PlaylistDuracionTest {
    @Test
    @DisplayName("Dada una playlist vacía, cuando se consulta su duración, devuelve cero")
    void dadaUnaPlaylistVaciaCuandoSeConsultaSuDuracionDevuelveCero() {
        Playlist playlist = new Playlist(1, "Playlist");

        Assertions.assertEquals(0, playlist.getDuracionEnSegundos());
    }

    @Test
    @DisplayName("Dada una playlist con distintos contenidos, cuando se consulta su duración, devuelve la suma total")
    void dadaUnaplaylistConCOntenidosDisintosDevuelveLaDuracionTotal() {

        Playlist playlist = new Playlist(1, "Playlist");

        Cancion cancion = new Cancion(2, "Gasolina", 250, "Daddy Yankee", "reggaeton", false, 500000);

        AudioLibro audioLibro = new AudioLibro(3, "Libro", 3600, "Autor", false);

        Podcast podcast = new Podcast(4, "Podcast", "Conductor", true);

        Episodio episodio1 = new Episodio(5, "Episodio 1", 500, true, 1, podcast);
        Episodio episodio2 = new Episodio(6, "Episodio 2", 300, true, 2, podcast);
        Episodio episodio3 = new Episodio(7, "Episodio 3", 450, true, 3, podcast);

        podcast.agregarEpisodio(episodio1);
        podcast.agregarEpisodio(episodio2);
        podcast.agregarEpisodio(episodio3);

        playlist.agregarElemento(cancion);
        playlist.agregarElemento(audioLibro);
        playlist.agregarElemento(podcast);

        Assertions.assertEquals(5100, playlist.getDuracionEnSegundos());
    }

    @Test
    @DisplayName("Dada una playlist con un álbum, cuando se consulta su duración, incluye todas las canciones del álbum")
    void dadaUnaPlaylistConUnAlbumCuandoSeConsultaSuDuracionIncluyeTodasLasCancionesDelAlbum() {
        Playlist playlist = new Playlist(1, "Playlist");

        Album album = new Album(2, "Thriller", "Michael Jackson");

        Cancion cancion1 = new Cancion(3, "Billie Jean", 230, "Michael Jackson", "pop", false, 70000000);
        Cancion cancion2 = new Cancion(4, "Beat It", 300, "Michael Jackson", "pop", false, 50000000);
        Cancion cancion3 = new Cancion(5, "Thriller", 280, "Michael Jackson", "pop", false, 60000000);

        album.agregarCancion(cancion1);
        album.agregarCancion(cancion2);
        album.agregarCancion(cancion3);

        playlist.agregarElemento(album);

        Assertions.assertEquals(810, playlist.getDuracionEnSegundos());
    }

    @Test
    @DisplayName("Dada una playlist que contiene otra playlist, cuando se consulta su duración, incluye todos sus contenidos")
    void dadaUnaPlaylistQueContieneOtraPlaylistCuandoSeConsultaSuDuracionIncluyeTodosSusContenidos() {
        Playlist playlistA = new Playlist(1, "Playlist A");
        Playlist playlistB = new Playlist(2, "Playlist B");

        Cancion cancion1 = new Cancion(3, "Gasolina", 200, "Daddy Yankee", "reggaeton", false, 500000);
        Cancion cancion2 = new Cancion(4, "Danza Kuduro", 300, "Don Omar", "reggaeton", false, 400000);

        AudioLibro audioLibro = new AudioLibro(5, "Libro", 3600, "Autor", false);

        playlistA.agregarElemento(cancion1);

        playlistB.agregarElemento(cancion2);
        playlistB.agregarElemento(audioLibro);

        playlistA.agregarElemento(playlistB);

        Assertions.assertEquals(4100, playlistA.getDuracionEnSegundos());
    }

}
