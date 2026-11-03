package ar.edu.unahur.obj2.parcial2.planes;

public interface PlanSuscripcion {
    Integer costoMensual();
    Calidad calidadMaxima();
    Boolean permiteContenidoPremium();
    Boolean permiteDescargas();
    Boolean tienePublicidad();
}
