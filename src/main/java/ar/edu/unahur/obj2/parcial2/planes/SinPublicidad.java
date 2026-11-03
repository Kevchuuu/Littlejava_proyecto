package ar.edu.unahur.obj2.parcial2.planes;

public class SinPublicidad extends PlanDecorador{
    public SinPublicidad(PlanSuscripcion plan){
        super(plan);
    }

    @Override
    protected Integer costoAdicional() {
        return Configuracion.COSTO_SIN_PUBLICIDAD;
    }

    @Override 
    public Boolean tienePublicidad(){ 
        return false;
    }
}
