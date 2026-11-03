package ar.edu.unahur.obj2.parcial2.catalogo;

import ar.edu.unahur.obj2.parcial2.planes.Configuracion;

public class Cancion extends Pista{
    private final String artista;
    private final String genero;
    private Integer cantidadReproducciones;

    public Cancion(Integer id, String titulo, Integer duracionEnSegundos, String artista, String genero,
            Boolean esPremium, Integer cantidadReproducciones) {
        super(id, titulo, duracionEnSegundos, esPremium);
        this.artista = artista;
        this.genero = genero;
        this.cantidadReproducciones = cantidadReproducciones;
    }

    public String getArtista() {
        return artista;
    }
    
    public String getGenero() {
        return genero;
    }

    public Integer getCantidadReproducciones() {
        return cantidadReproducciones;
    }

    public void setCantidadReproducciones(Integer cantidadReproducciones) {
        this.cantidadReproducciones = cantidadReproducciones;
    }

    @Override
    protected Double getCoeficienteRegalia() {
        return Configuracion.COEFICIENTE_CANCION;
    } 
}
