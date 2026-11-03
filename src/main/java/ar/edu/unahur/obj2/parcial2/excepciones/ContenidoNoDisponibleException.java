package ar.edu.unahur.obj2.parcial2.excepciones;

public class ContenidoNoDisponibleException extends StreamHubException {
    public ContenidoNoDisponibleException() {
        super("Contenido no disponible: requiere plan premium.");
    }

    public ContenidoNoDisponibleException(String mensaje) {
        super(mensaje);
    }
}