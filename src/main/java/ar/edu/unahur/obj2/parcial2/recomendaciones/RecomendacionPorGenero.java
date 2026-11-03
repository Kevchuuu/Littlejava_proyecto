package ar.edu.unahur.obj2.parcial2.recomendaciones;

import java.util.List;

import ar.edu.unahur.obj2.parcial2.catalogo.Cancion;
import ar.edu.unahur.obj2.parcial2.catalogo.IElementoDelCatalogo;
import ar.edu.unahur.obj2.parcial2.planes.Configuracion;
import ar.edu.unahur.obj2.parcial2.usuario.Usuario;

public class RecomendacionPorGenero implements IAlgoritmoRecomendacion {
    @Override
    public List<Cancion> recomendar(List<IElementoDelCatalogo> catalogo, Usuario usuario, List<Usuario> otrosUsuarios) {
        Cancion ultima = usuario.getUltimaCancion();
        if (ultima == null) return List.of();

        return catalogo.stream()
                .flatMap(e -> e.getPistas().stream())
                .filter(Cancion.class::isInstance)
                .map(Cancion.class::cast)
                .filter(c -> c.getGenero().equalsIgnoreCase(ultima.getGenero()))
                .filter(c -> !usuario.getHistorial().contains(c))
                .distinct()
                .limit(Configuracion.MAXIMO_RECOMENDACIONES)
                .toList();
    }
}