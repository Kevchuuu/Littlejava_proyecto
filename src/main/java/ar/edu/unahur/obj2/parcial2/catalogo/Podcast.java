package ar.edu.unahur.obj2.parcial2.catalogo;

import java.util.ArrayList;
import java.util.List;

//Kevin: representaria una "hoja" del composite.
public class Podcast extends ElementoCatalogo{
    private final String conductor;
    private final Boolean esPremium;
    private final List<Episodio> episodios;

    public Podcast(Integer id, String titulo, String conductor, Boolean esPremium) {
        super(id, titulo);
        this.conductor = conductor;
        this.esPremium = esPremium;
        this.episodios = new ArrayList<>();
    }

    public String getConductor() {
        return conductor;
    }

    public Boolean esPremium() { 
        return esPremium; 
    }

    public void agregarEpisodio(Episodio episodio) {
        episodios.add(episodio);
    }

    @Override
    public Integer getDuracionEnSegundos() {
        return episodios.stream()
                .mapToInt(Episodio::getDuracionEnSegundos)
                .sum();
    }

    @Override
    public Integer getCantidadPistas() {
        return episodios.size();
    }

    @Override
    public List<Pista> getPistas() {
        return new ArrayList<>(episodios);
    }

    @Override
    protected Double getCoeficienteRegalia() {
        return 0.12;
    }
}