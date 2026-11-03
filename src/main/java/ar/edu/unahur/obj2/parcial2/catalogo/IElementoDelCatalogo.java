package ar.edu.unahur.obj2.parcial2.catalogo;

import java.util.List;

public interface IElementoDelCatalogo {
    Integer getId();
    String getTitulo();
    
    // Kevin: Lo declaro como interfaz al elemento catalogo ya que el patron composite requiere construir estructuras jerárquicas 
    // donde los elementos tanto hojas o compuestos sean tratardos de forma unica mediante una interfaz común.
    Integer getDuracionEnSegundos();
    Integer getCantidadPistas();
    List<Pista> getPistas();
    Double getCostoRegalias(Integer reproducciones);
}