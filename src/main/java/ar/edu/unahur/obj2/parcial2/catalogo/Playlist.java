package ar.edu.unahur.obj2.parcial2.catalogo;

import ar.edu.unahur.obj2.parcial2.excepciones.PlaylistCiclicaException;

public class Playlist extends Contenedor  {

    public Playlist(Integer id, String titulo) {
        super(id, titulo);
    }

    private Boolean contienePlaylist(Playlist playlistBuscada) {
        return this == playlistBuscada || elementos.stream()
                .anyMatch(elem -> elem instanceof Playlist && 
                    ((Playlist) elem).contienePlaylist(playlistBuscada));
    }

    public void agregarElemento(ElementoCatalogo elemento) {
        if (elemento instanceof Playlist && ((Playlist) elemento).contienePlaylist(this)) {
            throw new PlaylistCiclicaException();
        }
        elementos.add(elemento);
    }

    public void removerElemento(ElementoCatalogo elemento) {
        elementos.remove(elemento);
    }
}
