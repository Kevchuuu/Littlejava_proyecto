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

public class PlaylistContenidoTest {
    @Test 
    @DisplayName("Dada una playlist vacía, cuando se agregan distintos contenidos, devuelve todas sus pistas")
    void dadaUnaPlaylistVaciaCuandoSeAgreganDistintosContenidosDevuelveTodasSusPistas(){
        Playlist playlist = new Playlist(123, "playlist 1");

        Cancion cancion1 = new Cancion(2, "Gasolina",250, "Daddy Yankee", "reggaeton", false, 500000);

        Podcast podcast = new Podcast(2, "podcast", "conductor", true);

        Episodio episodio1 = new Episodio(1, "Episodio 1", 500, true, 1, podcast);

        Episodio episodio2 = new Episodio(2, "Episodio 2", 300, true, 2, podcast);

        Episodio episodio3 = new Episodio(3, "Episodio 3", 450, true, 3, podcast);

        podcast.agregarEpisodio(episodio1);
        podcast.agregarEpisodio(episodio2);
        podcast.agregarEpisodio(episodio3);

        AudioLibro audioLibro = new AudioLibro(6, "audio libro", 3600, "autor", false);

        Album album = new Album(1, "Thriller", "Michael Jackson");

        Cancion cancionAlbum1 = new Cancion(2,"Billie Jean", 230, "Michael Jackson", "pop", false, 70000000);
        Cancion cancionAlbum2 = new Cancion(3,"Beat It", 300, "Michael Jackson", "pop", false, 50000000);
        Cancion cancionAlbum3 = new Cancion(4,"Thriller", 280, "Michael Jackson", "pop", false, 60000000);

        album.agregarCancion(cancionAlbum1);
        album.agregarCancion(cancionAlbum2);
        album.agregarCancion(cancionAlbum3);

        playlist.agregarElemento(cancion1);
        playlist.agregarElemento(podcast);
        playlist.agregarElemento(audioLibro);
        playlist.agregarElemento(album);
        
        List<Pista> listaEsperada = List.of( cancion1, episodio1, episodio2,episodio3, audioLibro, cancionAlbum1,  cancionAlbum2,cancionAlbum3);
        
        Assertions.assertEquals(listaEsperada, playlist.getPistas() );

    }

    @Test 
    @DisplayName("Dada una playlist con distintos contenidos, cuando se consulta la cantidad de pistas, devuelve el total")
    void dadaUnaPlaylistConDistintosContenidosDevuelveLaCantidadDePistas(){
        Playlist playlist = new Playlist(123, "playlist 1");

        Cancion cancion1 = new Cancion(2, "Gasolina",250, "Daddy Yankee", "reggaeton", false, 500000);

        Podcast podcast = new Podcast(2, "podcast", "conductor", true);

        Episodio episodio1 = new Episodio(1, "Episodio 1", 500, true, 1, podcast);

        Episodio episodio2 = new Episodio(2, "Episodio 2", 300, true, 2, podcast);

        Episodio episodio3 = new Episodio(3, "Episodio 3", 450, true, 3, podcast);

        podcast.agregarEpisodio(episodio1);
        podcast.agregarEpisodio(episodio2);
        podcast.agregarEpisodio(episodio3);

        AudioLibro audioLibro = new AudioLibro(6, "audio libro", 3600, "autor", false);

        Album album = new Album(1, "Thriller", "Michael Jackson");

        Cancion cancionAlbum1 = new Cancion(2,"Billie Jean", 230, "Michael Jackson", "pop", false, 70000000);
        Cancion cancionAlbum2 = new Cancion(3,"Beat It", 300, "Michael Jackson", "pop", false, 50000000);
        Cancion cancionAlbum3 = new Cancion(4,"Thriller", 280, "Michael Jackson", "pop", false, 60000000);

        album.agregarCancion(cancionAlbum1);
        album.agregarCancion(cancionAlbum2);
        album.agregarCancion(cancionAlbum3);

        playlist.agregarElemento(cancion1);
        playlist.agregarElemento(podcast);
        playlist.agregarElemento(audioLibro);
        playlist.agregarElemento(album);
        
        Assertions.assertEquals(8, playlist.getCantidadPistas());
    }

    @Test
    @DisplayName("Dada una playlist con elementos, cuando se elimina uno existente, deja de formar parte de ella")
    void dadaUnaPlaylistConElementosCuandoSeEliminaUnoExistenteDejaDeFormarParteDeElla() {
    
        Playlist playlist = new Playlist(1, "Playlist");

        Cancion cancion = new Cancion(2, "Gasolina", 250, "Daddy Yankee", "reggaeton", false, 500000);
        AudioLibro audioLibro = new AudioLibro(3, "Libro", 3600, "Autor", false);

        playlist.agregarElemento(cancion);
        playlist.agregarElemento(audioLibro);


        playlist.removerElemento(cancion);

        List<Pista> pistasEsperadas = List.of(audioLibro);

        Assertions.assertEquals(pistasEsperadas, playlist.getPistas());
    }

}
