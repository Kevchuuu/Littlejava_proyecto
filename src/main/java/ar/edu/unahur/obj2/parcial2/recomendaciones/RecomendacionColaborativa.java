package ar.edu.unahur.obj2.parcial2.recomendaciones;

import java.util.Comparator;
import java.util.List;
import ar.edu.unahur.obj2.parcial2.catalogo.Cancion;
import ar.edu.unahur.obj2.parcial2.catalogo.IElementoDelCatalogo;
import ar.edu.unahur.obj2.parcial2.planes.Configuracion;
import ar.edu.unahur.obj2.parcial2.usuario.Usuario;

public class RecomendacionColaborativa implements IAlgoritmoRecomendacion {
    @Override
    public List<Cancion> recomendar(List<IElementoDelCatalogo> catalogo, Usuario usuario, List<Usuario> otrosUsuarios) {
        List<Usuario> similares = otrosUsuarios.stream()
                .filter(otro -> !otro.equals(usuario))
                .filter(otro -> cancionesEnComun(usuario, otro) >= Configuracion.CANCIONES_SIMILARES_MINIMAS)
                .toList();

        if (similares.isEmpty()) return List.of();

        return catalogo.stream()
                .flatMap(e -> e.getPistas().stream())
                .filter(Cancion.class::isInstance)
                .map(Cancion.class::cast)
                .filter(c -> !usuario.getHistorial().contains(c))
                .filter(c -> oyentesSimilares(c, similares) > 0)
                .distinct()
                .sorted(Comparator.comparingInt((Cancion c) -> oyentesSimilares(c, similares)).reversed())
                .limit(Configuracion.MAXIMO_RECOMENDACIONES)
                .toList();
    }

    private Integer cancionesEnComun(Usuario u1, Usuario u2) {
        return u1.getHistorial().stream()
                .filter(Cancion.class::isInstance)
                .filter(p -> u2.getHistorial().contains(p))
                .toList()
                .size();
    }

    private Integer oyentesSimilares(Cancion cancion, List<Usuario> similares) {
        return similares.stream()
                .filter(u -> u.getHistorial().contains(cancion))
                .toList()
                .size();
    }
}