package ar.edu.unahur.obj2.parcial2.Consola;

public class Pause implements Accion {
 
    @Override
    public void ejecutar(Reproductor reproductor) {
        reproductor.pausar();
    }
}
