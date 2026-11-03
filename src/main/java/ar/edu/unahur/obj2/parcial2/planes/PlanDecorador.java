package ar.edu.unahur.obj2.parcial2.planes;

public abstract class PlanDecorador implements PlanSuscripcion {
    protected PlanSuscripcion plan;

    public PlanDecorador(PlanSuscripcion plan) {
        this.plan = plan;
    }

    @Override 
    public Integer costoMensual() {
        return plan.costoMensual() + costoAdicional();
    }

    protected abstract Integer costoAdicional();

    @Override 
    public Calidad calidadMaxima() {
        return plan.calidadMaxima();
    }

    @Override 
    public Boolean permiteContenidoPremium() {
        return plan.permiteContenidoPremium();
    }

    @Override 
    public Boolean permiteDescargas() {
        return plan.permiteDescargas();
    }

    @Override 
    public Boolean tienePublicidad() {
        return plan.tienePublicidad();
    }
}
