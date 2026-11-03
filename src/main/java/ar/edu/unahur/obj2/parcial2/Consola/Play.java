package ar.edu.unahur.obj2.parcial2.Consola;

import ar.edu.unahur.obj2.parcial2.catalogo.Pista;

public class Play implements Accion {
    private final Pista pista;
 
    public Play(Pista pista) {
        this.pista = pista;
    }
 
    @Override
    public void ejecutar(Reproductor reproductor) {
        reproductor.reproducir(pista);
    }
}
