package ar.edu.unahur.obj2.parcial2.Consola;

import java.util.List;

import ar.edu.unahur.obj2.parcial2.catalogo.IElementoDelCatalogo;
import ar.edu.unahur.obj2.parcial2.catalogo.Pista;

public class AddToQueue implements Accion {
    private final IElementoDelCatalogo elemento;
    private List<Pista> agregadas;   

    public AddToQueue(IElementoDelCatalogo elemento) {
        this.elemento = elemento;
    }

    @Override
    public void ejecutar(Reproductor reproductor) {
        
        this.agregadas = List.copyOf(elemento.getPistas());
        reproductor.encolar(agregadas);
    }

    @Override
    public Boolean admiteDeshacer() {
        return true;
    }

    @Override
    public void deshacer(Reproductor reproductor) {
        reproductor.desencolarUltimas(agregadas);
    }
}