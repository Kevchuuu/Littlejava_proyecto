package ar.edu.unahur.obj2.parcial2.Consola;

import ar.edu.unahur.obj2.parcial2.planes.Calidad;

public class CambiarCalidad implements Accion {
    private Calidad calidad;
    private Calidad calidadPrevia;

    public CambiarCalidad(Calidad calidad) {
        this.calidad = calidad;
    }

    public void corregir(Calidad nuevaCalidad) {
        this.calidad = nuevaCalidad;
    }

    @Override
    public void ejecutar(Reproductor reproductor) {
        this.calidadPrevia = reproductor.getCalidad();
        reproductor.cambiarCalidad(calidad);
    }

    @Override
    public Boolean admiteDeshacer() {
        return true;
    }

    @Override
    public void deshacer(Reproductor reproductor) {
        reproductor.cambiarCalidad(calidadPrevia);
    }
}
