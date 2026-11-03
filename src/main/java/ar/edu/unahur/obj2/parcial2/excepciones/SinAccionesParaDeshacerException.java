package ar.edu.unahur.obj2.parcial2.excepciones;

public class SinAccionesParaDeshacerException extends StreamHubException {
    public SinAccionesParaDeshacerException() {
        super("No hay acciones para deshacer.");
    }
    public SinAccionesParaDeshacerException(String mensaje) {
        super(mensaje);
    }
}
