package ar.edu.unahur.obj2.parcial2.streamHubTests.playlistTest;
import ar.edu.unahur.obj2.parcial2.catalogo.Playlist;
import ar.edu.unahur.obj2.parcial2.catalogo.Contenedor;
import ar.edu.unahur.obj2.parcial2.catalogo.Cancion;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import ar.edu.unahur.obj2.parcial2.catalogo.Album;
import ar.edu.unahur.obj2.parcial2.catalogo.AudioLibro;
import ar.edu.unahur.obj2.parcial2.catalogo.Podcast;
import ar.edu.unahur.obj2.parcial2.catalogo.Episodio;
import ar.edu.unahur.obj2.parcial2.catalogo.Pista;
import ar.edu.unahur.obj2.parcial2.excepciones.PlaylistCiclicaException;
public class PlaylistCiclicaTest {
    @Test 
    @DisplayName("Dada una playlist, cuando intenta agregarse a sí misma, lanza una excepción")
    void dadaUnaPlaylistCuandoIntentaAgregarseASiMismaLanzaExcepcion(){
        Playlist playlistA = new Playlist(1, "Playlist");

        Cancion cancion = new Cancion(2, "Gasolina", 250, "Daddy Yankee", "reggaeton", false, 500000);
        AudioLibro audioLibro = new AudioLibro(3, "Libro", 3600, "Autor", false);

        playlistA.agregarElemento(cancion);
        playlistA.agregarElemento(audioLibro);

        Assertions.assertThrows(PlaylistCiclicaException.class, () ->{
            playlistA.agregarElemento(playlistA);
        });

    }

    @Test
    @DisplayName("Dadas tres playlists anidadas, cuando se intenta generar un ciclo indirecto, lanza una excepción")
    void dadasTresPlaylistsAnidadasCuandoSeIntentaGenerarUnCicloIndirectoLanzaExcepcion() {

        Playlist playlistA = new Playlist(1, "Playlist A");
        Playlist playlistB = new Playlist(2, "Playlist B");
        Playlist playlistC = new Playlist(3, "Playlist C");

        playlistA.agregarElemento(playlistB);
        playlistB.agregarElemento(playlistC);

        Assertions.assertThrows(PlaylistCiclicaException.class, () -> {
        playlistC.agregarElemento(playlistA);
        });
    }

    @Test
    @DisplayName("Dadas dos playlists, cuando una contiene a la otra, permite agregarlas sin generar ciclos")
    void dadasDosPlaylistsCuandoUnaContieneALaOtraPermiteAgregarlasSinGenerarCiclos() {
    
        Playlist playlistA = new Playlist(1, "Playlist A");
        Playlist playlistB = new Playlist(2, "Playlist B");

        Cancion cancion1 = new Cancion(3, "Gasolina", 250, "Daddy Yankee", "reggaeton", false, 500000);
        AudioLibro audioLibro = new AudioLibro(4, "Libro", 3600, "Autor", false);

        playlistB.agregarElemento(cancion1);
        playlistB.agregarElemento(audioLibro);

        playlistA.agregarElemento(playlistB);

        List<Pista> pistasEsperadas = List.of(cancion1,audioLibro);

        Assertions.assertEquals(pistasEsperadas, playlistA.getPistas());
        Assertions.assertEquals(2, playlistA.getCantidadPistas());
    }

}
