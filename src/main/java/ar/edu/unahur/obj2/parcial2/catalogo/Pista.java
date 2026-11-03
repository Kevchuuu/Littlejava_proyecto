package ar.edu.unahur.obj2.parcial2.catalogo;

import java.util.List;

//Kevin: representaria las hojas del composite a parte del podcast.
public abstract class Pista extends ElementoCatalogo {
    private final Integer duracionEnSegundos;
    private final Boolean esPremium;

    public Pista(Integer id, String titulo, Integer duracionEnSegundos, Boolean esPremium) {
        super(id, titulo);
        this.duracionEnSegundos = duracionEnSegundos;
        this.esPremium = esPremium;
    }

    @Override
    public Integer getDuracionEnSegundos() {
        return duracionEnSegundos;
    }

    @Override
    public Integer getCantidadPistas() {
        return 1;
    }

    @Override
    public List<Pista> getPistas() {
        return List.of(this);
    }

    public Boolean esPremium() {
        return esPremium;
    }
}
