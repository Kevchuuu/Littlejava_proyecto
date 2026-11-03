package ar.edu.unahur.obj2.parcial2.Consola;

public class Anomalia {
    private final Accion accion;
    private final String motivo;

    public Anomalia(Accion accion, String motivo) {
        this.accion = accion;
        this.motivo = motivo;
    }

    public Accion getAccion() { 
        return accion; 
    }
    public String getMotivo() { 
        return motivo; 
    }
}
