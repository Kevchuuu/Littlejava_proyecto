package ar.edu.unahur.obj2.parcial2.recomendaciones;

import java.util.Comparator;
import java.util.List;

import ar.edu.unahur.obj2.parcial2.catalogo.Cancion;
import ar.edu.unahur.obj2.parcial2.catalogo.IElementoDelCatalogo;
import ar.edu.unahur.obj2.parcial2.planes.Configuracion;
import ar.edu.unahur.obj2.parcial2.usuario.Usuario;

public class RecomendacionPorHistorial implements IAlgoritmoRecomendacion {
    @Override
    public List<Cancion> recomendar(List<IElementoDelCatalogo> catalogo, Usuario usuario, List<Usuario> otrosUsuarios) {
        Cancion ultima = usuario.getUltimaCancion();
        if (ultima == null) return List.of();

        return catalogo.stream()
                .flatMap(e -> e.getPistas().stream())
                .filter(Cancion.class::isInstance)
                .map(Cancion.class::cast)
                .filter(c -> c.getArtista().equalsIgnoreCase(ultima.getArtista()))
                .filter(c -> !usuario.getHistorial().contains(c))
                .distinct()
                .sorted(Comparator.comparingInt(Cancion::getCantidadReproducciones).reversed())
                .limit(Configuracion.MAXIMO_RECOMENDACIONES)
                .toList();
    }
}