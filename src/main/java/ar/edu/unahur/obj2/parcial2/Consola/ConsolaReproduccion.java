package ar.edu.unahur.obj2.parcial2.Consola;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Deque;

import ar.edu.unahur.obj2.parcial2.excepciones.SinAccionesParaDeshacerException;

public class ConsolaReproduccion {
    private final Reproductor reproductor;
    private final Deque<Accion> pendientes = new ArrayDeque<>();
    private final List<Accion> deshacibles = new ArrayList<>();
    private final List<Anomalia> anomalias = new ArrayList<>();

    public ConsolaReproduccion(Reproductor reproductor) {
        this.reproductor = reproductor;
    }

    public void encolar(Accion accion) {
        pendientes.addLast(accion);
    }

    public void ejecutarLote() {
        while (!pendientes.isEmpty()) {
            Accion accion = pendientes.pollFirst();
            try {
                accion.ejecutar(reproductor);
                if (accion.admiteDeshacer()) {
                    deshacibles.add(accion);       
                }
            } catch (RuntimeException  exception) {
                anomalias.add(new Anomalia(accion, exception.getMessage()));  
            }
        }
    }

    public void deshacerUltimo() {
        if (deshacibles.isEmpty()) {
            throw new SinAccionesParaDeshacerException("Sin accion para deshacer");
        }
        Accion ultima = deshacibles.remove(deshacibles.size() - 1);
        ultima.deshacer(reproductor);
    }

    public List<Anomalia> getAnomalias() {
        return anomalias;
    }
}
