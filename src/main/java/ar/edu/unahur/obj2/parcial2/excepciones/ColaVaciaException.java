package ar.edu.unahur.obj2.parcial2.excepciones;

public class ColaVaciaException extends RuntimeException {
    public ColaVaciaException(String mensaje) {
        super(mensaje);
    }
}