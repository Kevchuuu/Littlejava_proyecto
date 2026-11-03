package ar.edu.unahur.obj2.parcial2.recomendaciones;

import java.util.List;
import ar.edu.unahur.obj2.parcial2.catalogo.Cancion;
import ar.edu.unahur.obj2.parcial2.catalogo.IElementoDelCatalogo;
import ar.edu.unahur.obj2.parcial2.usuario.Usuario;

public class MotorRecomendaciones {
   private IAlgoritmoRecomendacion algoritmo;

    public MotorRecomendaciones(IAlgoritmoRecomendacion algoritmoInicial) {
        this.algoritmo = algoritmoInicial;
    }

    public void setAlgoritmo(IAlgoritmoRecomendacion nuevoAlgoritmo) {
        this.algoritmo = nuevoAlgoritmo;
    }

    public List<Cancion> recomendar(List<IElementoDelCatalogo> catalogo, Usuario usuario, List<Usuario> otrosUsuarios) {
        return algoritmo.recomendar(catalogo, usuario, otrosUsuarios);
    }
}