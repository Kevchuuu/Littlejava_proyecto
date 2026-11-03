package ar.edu.unahur.obj2.parcial2.excepciones;

public class CalidadNoPermitidaException extends StreamHubException {
    public CalidadNoPermitidaException() {
        super("Calidad no permitida para el plan vigente (no se puede cambiar a una calidad superior a la máxima del plan).");
    }
   
    public CalidadNoPermitidaException(String mensaje) {
        super(mensaje);
    }
}