package ar.edu.unahur.obj2.parcial2.excepciones;

public class PlaylistCiclicaException extends RuntimeException {
    public PlaylistCiclicaException(){
        super("La playlist no puede contenerse a si misma.");
    }

    public PlaylistCiclicaException(String mensaje){
        super(mensaje);
    }
}
