package ar.edu.unahur.obj2.parcial2.planes;

public class AltaCalidad extends PlanDecorador {
    public AltaCalidad(PlanSuscripcion plan) {
        super(plan);
    }

    @Override 
    protected Integer costoAdicional() {
        return Configuracion.COSTO_ALTA_CALIDAD;
    }

    @Override
    public Calidad calidadMaxima() {
        return plan.calidadMaxima().mayor(Calidad.ALTA);
    }

    @Override 
    public  Boolean permiteContenidoPremium(){ 
        return true;
    }
}
