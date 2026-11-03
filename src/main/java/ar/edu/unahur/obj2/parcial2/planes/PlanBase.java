package ar.edu.unahur.obj2.parcial2.planes;

public class PlanBase implements PlanSuscripcion {
    
    @Override
    public Integer costoMensual() {
        return Configuracion.COSTO_PLAN_BASE;
    }

    @Override 
    public Calidad calidadMaxima() {
        return Calidad.ESTANDAR;
    }

    @Override
    public Boolean permiteContenidoPremium() {
        return false;
    }

    @Override  
    public Boolean permiteDescargas() {
        return false;
    }

    @Override 
    public Boolean tienePublicidad() {
        return true;
    }
}
