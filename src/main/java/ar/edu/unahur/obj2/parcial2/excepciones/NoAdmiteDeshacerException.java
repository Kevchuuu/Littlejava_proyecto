package ar.edu.unahur.obj2.parcial2.excepciones;

public class NoAdmiteDeshacerException extends RuntimeException {
    public NoAdmiteDeshacerException(String mensaje) {
        super(mensaje);
    }
}
