package ar.edu.unahur.obj2.parcial2.planes;

public class DescargasOffline extends PlanDecorador{
    public DescargasOffline(PlanSuscripcion plan){
        super(plan);
    }

    @Override
    protected Integer costoAdicional() {
        return Configuracion.COSTO_DESCARGAS;
    }

    @Override 
    public Boolean permiteDescargas(){ 
        return true;
    }
}
