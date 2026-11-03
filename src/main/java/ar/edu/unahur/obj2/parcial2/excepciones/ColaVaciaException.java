package ar.edu.unahur.obj2.parcial2.excepciones;

public class ColaVaciaException extends StreamHubException {

    public ColaVaciaException() {
        super("No se puede realizar Skip: la cola de reproducción está vacía.");
    }

    public ColaVaciaException(String mensaje) {
        super(mensaje);
    }  
}