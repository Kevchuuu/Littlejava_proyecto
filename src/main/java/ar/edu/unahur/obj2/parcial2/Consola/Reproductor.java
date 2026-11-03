package ar.edu.unahur.obj2.parcial2.Consola;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

import ar.edu.unahur.obj2.parcial2.usuario.Usuario;
import ar.edu.unahur.obj2.parcial2.catalogo.Cancion;
import ar.edu.unahur.obj2.parcial2.catalogo.Pista;
import ar.edu.unahur.obj2.parcial2.excepciones.CalidadNoPermitidaException;
import ar.edu.unahur.obj2.parcial2.excepciones.ColaVaciaException;
import ar.edu.unahur.obj2.parcial2.excepciones.ContenidoNoDisponibleException;
import ar.edu.unahur.obj2.parcial2.planes.Calidad;

 

public class Reproductor {
    private final Usuario usuario;
    private Pista actual;
    private Estado estado = Estado.DETENIDO;
    private Calidad calidad = Calidad.ESTANDAR;
    private final Deque<Pista> cola = new ArrayDeque<>();
 
    public Reproductor(Usuario usuario) {
        this.usuario = usuario;
    }
 

    public void reproducir(Pista pista) {
        if (pista.esPremium() && !usuario.getPlan().permiteContenidoPremium()) {
            throw new ContenidoNoDisponibleException();
        }
        actual = pista;
        estado = Estado.REPRODUCIENDO;
        usuario.getHistorial().add(pista);

        //Agrego esto porque el dominio dice que cada vez que una pista comienza a reproducirse se agregue al historial 
        //del usuario y, si es una canción, se incremente su contador de reproducciones
        if (pista instanceof Cancion cancion) {
            cancion.setCantidadReproducciones(cancion.getCantidadReproducciones() + 1);
        }
    }
 
    public void pausar() {
        if (estado == Estado.REPRODUCIENDO) {
            estado = Estado.PAUSADO;
        }
    }
 
    public void cambiarCalidad(Calidad nueva) {
        Calidad maxima = usuario.getPlan().calidadMaxima();
        if (nueva.compareTo(maxima) > 0) {
            throw new CalidadNoPermitidaException();
        }
        calidad = nueva;
    }
 

    public Pista primeraDeCola() {
        if (cola.isEmpty()) {
            throw new ColaVaciaException("Cola vacia");
        }
        return cola.peekFirst();
    }
 
    public void quitarPrimeraDeCola() {
        cola.removeFirst();
    }
 
    public void ponerAlFrente(Pista pista) {
        cola.addFirst(pista);
    }
 
    public void encolar(List<Pista> pistas) {
        cola.addAll(pistas);
    }
 
    public void revertirReproduccion(Pista reproducida, Pista pistaPrevia, Estado estadoPrevio) {
        List<Pista> historial = usuario.getHistorial();
        historial.remove(historial.size() - 1);

        //Al deshacer un Skip, se tiene que revertir el registro en el historial y en el contador de reproducciones tambien.
        if (reproducida instanceof Cancion cancion) {
            cancion.setCantidadReproducciones(cancion.getCantidadReproducciones() - 1);
        }

        actual = pistaPrevia;
        estado = estadoPrevio;
    }
 
    public Pista getActual() { 
        return actual; 
    }
    public Estado getEstado() { 
        return estado; 
    }
    public Calidad getCalidad() { 
        return calidad; 
    }
    public List<Pista> getCola() { 
        return new ArrayList<>(cola); 
    }
    public void desencolarUltimas(List<Pista> pistas) {
        pistas.forEach(pista -> cola.removeLast());
    }
}
