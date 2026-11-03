package ar.edu.unahur.obj2.parcial2.catalogo;

import ar.edu.unahur.obj2.parcial2.excepciones.ArtistaIncompatibleException;

public class Album extends Contenedor {
    private final String artista;

    public Album(Integer id, String titulo, String artista) {
        super(id, titulo);
        this.artista = artista;
    }

    public String getArtista() {
        return artista;
    }

    public void agregarCancion(Cancion cancion) {
        if (!this.artista.equalsIgnoreCase(cancion.getArtista())) {
            throw new ArtistaIncompatibleException();
        }
        elementos.add(cancion);
    }
}