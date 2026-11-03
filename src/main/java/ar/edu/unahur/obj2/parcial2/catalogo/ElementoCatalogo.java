package ar.edu.unahur.obj2.parcial2.catalogo;

public abstract class ElementoCatalogo implements IElementoDelCatalogo{
    private final Integer id;
    private final String titulo;

    public ElementoCatalogo(Integer id, String titulo) {
        this.id = id;
        this.titulo = titulo;
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public String getTitulo() {
        return titulo;
    }

    @Override
    public final Double getCostoRegalias(Integer reproducciones) {
        return getDuracionEnSegundos() * getCoeficienteRegalia() * reproducciones;
    }

    protected abstract Double getCoeficienteRegalia();
}