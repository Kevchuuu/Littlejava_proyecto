package ar.edu.unahur.obj2.parcial2.catalogo;

import ar.edu.unahur.obj2.parcial2.planes.Configuracion;

public class AudioLibro extends Pista {
    private final String autor;

    public AudioLibro(Integer id, String titulo, Integer duracionEnSegundos, String autor, Boolean esPremium) {
        super(id, titulo, duracionEnSegundos, esPremium);
        this.autor = autor;
    }

    public String getAutor() {
        return autor;
    }

    @Override
    protected Double getCoeficienteRegalia() {
        return Configuracion.COEFICIENTE_AUDIOLIBRO;
    } 
}
