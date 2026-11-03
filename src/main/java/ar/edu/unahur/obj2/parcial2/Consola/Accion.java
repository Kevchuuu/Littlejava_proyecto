package ar.edu.unahur.obj2.parcial2.Consola;

import ar.edu.unahur.obj2.parcial2.excepciones.NoAdmiteDeshacerException;

public interface Accion {
 
    void ejecutar(Reproductor reproductor);

    default Boolean admiteDeshacer() {
        return false;
    }
 
    default void deshacer(Reproductor reproductor) {
        throw new NoAdmiteDeshacerException();
    }
}
 
