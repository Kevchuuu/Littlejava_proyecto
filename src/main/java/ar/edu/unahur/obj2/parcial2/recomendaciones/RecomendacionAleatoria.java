package ar.edu.unahur.obj2.parcial2.recomendaciones;

import java.util.List;
import ar.edu.unahur.obj2.parcial2.catalogo.Cancion;
import ar.edu.unahur.obj2.parcial2.catalogo.IElementoDelCatalogo;
import ar.edu.unahur.obj2.parcial2.planes.Configuracion;
import ar.edu.unahur.obj2.parcial2.usuario.Usuario;

public class RecomendacionAleatoria implements IAlgoritmoRecomendacion {
   @Override
    public List<Cancion> recomendar(List<IElementoDelCatalogo> catalogo, Usuario usuario, List<Usuario> otrosUsuarios) {
        return catalogo.stream()
                .flatMap(elem -> elem.getPistas().stream())
                .filter(Cancion.class::isInstance)
                .map(Cancion.class::cast)
                .filter(cancion -> !usuario.getHistorial().contains(cancion)) 
                .distinct()
                .sorted((c1, c2) -> Double.compare(Math.random(), 0.5))
                .limit(Configuracion.MAXIMO_RECOMENDACIONES)
                .toList();
    }
}