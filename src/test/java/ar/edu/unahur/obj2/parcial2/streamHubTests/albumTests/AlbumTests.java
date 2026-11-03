package ar.edu.unahur.obj2.parcial2.streamHubTests.albumTests;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import ar.edu.unahur.obj2.parcial2.catalogo.Album;
import ar.edu.unahur.obj2.parcial2.catalogo.Cancion;
import ar.edu.unahur.obj2.parcial2.excepciones.ArtistaIncompatibleException;
import ar.edu.unahur.obj2.parcial2.catalogo.Pista;

public class AlbumTests {
    @Test
    @DisplayName("Dado un album vacio cuando se le consulta su duracion en segundos entonces es 0")
    void dadoUnAlbumVacioSuDuracionEnSegundosEs0(){
        Album album = new Album(1, "Thriller", "Michael Jackson");
        Assertions.assertEquals(0, album.getDuracionEnSegundos());
        
    }
    @Test
    @DisplayName("Dado un album vacio cuando se le consulta su cantidad de pistas es igual a 0")
    void dadoUnAlbumVacioSuCantidadDePistasEs0(){
        Album album = new Album(1, "Thriller", "Michael Jackson");
        Assertions.assertEquals(0, album.getCantidadPistas());
        
    }

    @Test
    @DisplayName("Dado un album de un artista cuando se quiere agregar una cancion de otro artista lanza excepcion")
    void dadoUnAlbumDeUnArtistaCuandoSeQuiereAgregarUnaCancionDeOtroLanzaExcepcion(){
        Album album = new Album(1, "Thriller", "Michael Jackson");

        Cancion cancion1 = new Cancion(2, "Gasolina",250, "Daddy Yankee", "reggaeton", false, 500000);
        Assertions.assertThrows(ArtistaIncompatibleException.class, ()->{
            album.agregarCancion(cancion1);
        });

    }

    @Test 
    @DisplayName ("Dadas 3 canciones del mismo artista cuando se quieren agregar al album se agregan correctamente")
    void dadasTresCancionesDelMismoArtistaSeAgreganAlbum(){
        Album album = new Album(1, "Thriller", "Michael Jackson");

        Cancion cancion1 = new Cancion(2,"Billie Jean", 230, "Michael Jackson", "pop", false, 70000000);
        Cancion cancion2 = new Cancion(3,"Beat It", 300, "Michael Jackson", "pop", false, 50000000);
        Cancion cancion3 = new Cancion(4,"Thriller", 280, "Michael Jackson", "pop", false, 60000000);

        album.agregarCancion(cancion1);
        album.agregarCancion(cancion2);
        album.agregarCancion(cancion3);

        List<Pista> listaEsperada = List.of(cancion1,cancion2,cancion3);
        Assertions.assertEquals(listaEsperada, album.getPistas());
    }

    @Test 
    @DisplayName ("Dado un album con 3 canciones cuando se suman la duracion de las canciones entonces devuelve la duracion del album")
    void dadoUnAlbumCon3CancionesDevuelveLaDuracionEnSegundos(){
        Album album = new Album(1, "Thriller", "Michael Jackson");

        Cancion cancion1 = new Cancion(2,"Billie Jean", 230, "Michael Jackson", "pop", false, 70000000);
        Cancion cancion2 = new Cancion(3,"Beat It", 300, "Michael Jackson", "pop", false, 50000000);
        Cancion cancion3 = new Cancion(4,"Thriller", 280, "Michael Jackson", "pop", false, 60000000);

        album.agregarCancion(cancion1);
        album.agregarCancion(cancion2);
        album.agregarCancion(cancion3);

        Assertions.assertEquals(810, album.getDuracionEnSegundos());
    }

    @Test 
    @DisplayName ("Dado un catalogo cuando se calcula la cantidad total de canciones entonces devuelve la cantidad correcta")
    void dadoUnAlbumConCancionesDevuelveLaCantidadDeCancionesQueTiene(){
        Album album = new Album(1, "Thriller", "Michael Jackson");

        Cancion cancion1 = new Cancion(2,"Billie Jean", 230, "Michael Jackson", "pop", false, 70000000);
        Cancion cancion2 = new Cancion(3,"Beat It", 300, "Michael Jackson", "pop", false, 50000000);
        Cancion cancion3 = new Cancion(4,"Thriller", 280, "Michael Jackson", "pop", false, 60000000);

        album.agregarCancion(cancion1);
        album.agregarCancion(cancion2);
        album.agregarCancion(cancion3);

        Assertions.assertEquals(3, album.getCantidadPistas());
    }

    @Test
    @DisplayName("Dado un album cuando se agrega una cancion con el artista en mayusculas entonces se agrega correctamente")
    void dadoUnAlbumCuandoSeAgregaUnaCancionConArtistaEnMayusculasSeAgregaCorrectamente() {
  
        Album album = new Album(1, "Thriller", "Michael Jackson");

        Cancion cancion = new Cancion(2, "Billie Jean", 230, "MICHAEL JACKSON", "pop", false, 70000000);

        album.agregarCancion(cancion);

        Assertions.assertEquals(List.of(cancion), album.getPistas());
    }
    @Test 
    @DisplayName ("Dado un album con canciones cuando se calcula el costo de regalias entonces devuelve el valor correcto")
    void dadoUnAlbumConCancionesCalculaElCostoDeRegalias(){
        Album album = new Album(1, "Thriller", "Michael Jackson");

        Cancion cancion1 = new Cancion(2,"Billie Jean", 230, "Michael Jackson", "pop", false, 70000000);
        Cancion cancion2 = new Cancion(3,"Beat It", 300, "Michael Jackson", "pop", false, 50000000);
        Cancion cancion3 = new Cancion(4,"Thriller", 280, "Michael Jackson", "pop", false, 60000000);

        album.agregarCancion(cancion1);
        album.agregarCancion(cancion2);
        album.agregarCancion(cancion3);

        Assertions.assertEquals(5670.0, album.getCostoRegalias(100));
    }
}
