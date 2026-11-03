package ar.edu.unahur.obj2.parcial2.excepciones;

public class ArtistaIncompatibleException extends StreamHubException {
    
    public ArtistaIncompatibleException() {
        super("No se puede agregar la canción: el artista no coincide con el álbum.");
    }
   
    public ArtistaIncompatibleException(String mensaje) {
        super(mensaje);
    }
}
