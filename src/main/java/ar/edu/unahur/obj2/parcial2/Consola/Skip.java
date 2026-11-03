package ar.edu.unahur.obj2.parcial2.Consola;

import ar.edu.unahur.obj2.parcial2.catalogo.Pista;

public class Skip implements Accion {
    private Pista reproducida;
    private Pista pistaPrevia;
    private Estado estadoPrevio;

    @Override
    public void ejecutar(Reproductor reproductor) {
        Pista siguiente = reproductor.primeraDeCola();
        Pista previa = reproductor.getActual();
        Estado estadoAnterior = reproductor.getEstado();

        reproductor.reproducir(siguiente);
        reproductor.quitarPrimeraDeCola();

        this.reproducida = siguiente;
        this.pistaPrevia = previa;
        this.estadoPrevio = estadoAnterior;
    }

    @Override
    public Boolean admiteDeshacer() {
        return true;
    }

    @Override
    public void deshacer(Reproductor reproductor) {
        reproductor.revertirReproduccion(reproducida, pistaPrevia, estadoPrevio);
        reproductor.ponerAlFrente(reproducida);
    }
}
