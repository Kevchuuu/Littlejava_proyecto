package ar.edu.unahur.obj2.parcial2.excepciones;

public class ArtistaIncompatibleException extends RuntimeException {
    public ArtistaIncompatibleException(){
        super("No se puede agregar una canción de un artista diferente al del álbum.");
    }
}
