package ar.edu.unahur.obj2.parcial2.catalogo;

import java.util.List;

public interface IElementoDelCatalogo {
    Integer getId();
    String getTitulo();
    Integer getDuracionEnSegundos();
    Integer getCantidadPistas();
    List<Pista> getPistas();
    Double getCostoRegalias(Integer reproducciones);
}