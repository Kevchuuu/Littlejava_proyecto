package ar.edu.unahur.obj2.parcial2.excepciones;

public class NoAdmiteDeshacerException extends StreamHubException {
    public NoAdmiteDeshacerException() {
        super("No se puede deshacer esta accion");
    }

    public NoAdmiteDeshacerException(String mensaje) {
        super(mensaje);
    }
}
