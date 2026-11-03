package ar.edu.unahur.obj2.parcial2.recomendaciones;

import java.util.List;
import ar.edu.unahur.obj2.parcial2.catalogo.Cancion;
import ar.edu.unahur.obj2.parcial2.catalogo.IElementoDelCatalogo;
import ar.edu.unahur.obj2.parcial2.usuario.Usuario;

public interface IAlgoritmoRecomendacion {
    List<Cancion> recomendar(List<IElementoDelCatalogo> catalogo, Usuario usuario, List<Usuario> otrosUsuarios);
}
