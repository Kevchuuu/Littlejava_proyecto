package ar.edu.unahur.obj2.parcial2.Consola;

import ar.edu.unahur.obj2.parcial2.planes.Calidad;

public class CambiarCalidad implements Accion {
    private Calidad calidad;   

    public CambiarCalidad(Calidad calidad) {
        this.calidad = calidad;
    }

    public void corregir(Calidad nuevaCalidad) {
        this.calidad = nuevaCalidad;
    }

    public Calidad getCalidad() {
        return calidad;
    }

    @Override
    public void ejecutar(Reproductor reproductor) {
        reproductor.cambiarCalidad(calidad);   
    }
}

