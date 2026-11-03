package ar.edu.unahur.obj2.parcial2.catalogo;

public class Episodio extends Pista {
    private final Integer numero;
    private final Podcast podcast;
    
    public Episodio(Integer id, String titulo, Integer duracionEnSegundos, Boolean esPremium, Integer numero, Podcast podcast) {
        super(id, titulo, duracionEnSegundos, esPremium);
        this.numero = numero;
        this.podcast = podcast;
    }

    public Integer getNumero() {
        return numero;
    }

    @Override
    public Boolean esPremium() {
        return podcast.esPremium();
    }
    
    @Override
    protected Double getCoeficienteRegalia() {
        return podcast.getCoeficienteRegalia();
    }  
}
