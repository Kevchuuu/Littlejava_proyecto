package ar.edu.unahur.obj2.parcial2.catalogo;

import java.util.ArrayList;
import java.util.List;

//Kevin: Hago una clase contenedor ya que tanto playlist y album comparten listas/metodos/operaciones similares, y asi evitamos repetir codigo. Esto vendria ser el "compuesto".
public abstract class Contenedor extends ElementoCatalogo { 
    protected final List<IElementoDelCatalogo> elementos = new ArrayList<>();

    public Contenedor(Integer id, String titulo) {
        super(id, titulo);
    }

    @Override 
    public Integer getDuracionEnSegundos(){
        return elementos.stream().mapToInt(c ->c.getDuracionEnSegundos()).sum();
    }

    @Override
    public Integer getCantidadPistas(){
        return elementos.stream()
            .mapToInt(e -> e.getCantidadPistas())
            .sum();
    }

    @Override
    public List<Pista> getPistas() {
        return elementos.stream()
            .flatMap(e -> e.getPistas().stream())
            .toList();
    }

    //Kevin: agrego un cambio sutil no cambia nada mas que en la forma como se lee e interpreta
    @Override
    protected Double getCoeficienteRegalia() {
        Integer duracionTotal = getDuracionEnSegundos();
        if (duracionTotal == 0) {
            return 0.0;
        }

        Double costoTotalElementosHijos = elementos.stream()
                .mapToDouble(e -> e.getCostoRegalias(1))
                .sum();

        return costoTotalElementosHijos / duracionTotal;
    }
}
