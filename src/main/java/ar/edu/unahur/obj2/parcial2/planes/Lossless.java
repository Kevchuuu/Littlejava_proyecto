package ar.edu.unahur.obj2.parcial2.planes;

public class Lossless extends PlanDecorador{
    public Lossless(PlanSuscripcion plan){
        super(plan);
    }

    @Override
    protected Integer costoAdicional() {
        return Configuracion.COSTO_LOSSLESS;
    }
    @Override
    public Calidad calidadMaxima() {
        return plan.calidadMaxima().mayor(Calidad.LOSSLESS);
    }

    @Override 
    public Boolean permiteContenidoPremium(){ 
        return true;
    }
}
