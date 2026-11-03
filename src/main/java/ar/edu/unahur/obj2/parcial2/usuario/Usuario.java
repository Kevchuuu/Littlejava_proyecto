package ar.edu.unahur.obj2.parcial2.usuario;

import java.util.ArrayList;
import java.util.List;
import ar.edu.unahur.obj2.parcial2.Consola.Reproductor;
import ar.edu.unahur.obj2.parcial2.catalogo.Cancion;
import ar.edu.unahur.obj2.parcial2.catalogo.Pista;
import ar.edu.unahur.obj2.parcial2.planes.PlanSuscripcion;

public class Usuario {
    private final String nombre;
    private final List<Pista> historial = new ArrayList<>();
    private final PlanSuscripcion plan;
    private final Reproductor reproductor;

    public Usuario(String nombre, PlanSuscripcion plan) {
        this.nombre = nombre;
        this.plan = plan;
        this.reproductor = new Reproductor(this);
    }

    public PlanSuscripcion getPlan() { 
        return plan; 
    }

    public Reproductor getReproductor() { 
        return reproductor; 
    }

    public String getNombre() {
        return nombre;
    }

    public List<Pista> getHistorial() {
        return historial;
    }

    public void agregarAlHistorial(Pista pista) {
        this.historial.add(pista);
    }

    public Cancion getUltimaCancion() {
        return historial.stream()
                .filter(Cancion.class::isInstance)
                .map(Cancion.class::cast)
                .reduce((primera, segunda) -> segunda)
                .orElse(null);
    }
}