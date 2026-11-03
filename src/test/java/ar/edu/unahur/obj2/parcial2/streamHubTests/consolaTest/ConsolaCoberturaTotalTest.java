package ar.edu.unahur.obj2.parcial2.streamHubTests.consolaTest;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ar.edu.unahur.obj2.parcial2.Consola.Accion;
import ar.edu.unahur.obj2.parcial2.Consola.AddToQueue;
import ar.edu.unahur.obj2.parcial2.Consola.Anomalia;
import ar.edu.unahur.obj2.parcial2.Consola.CambiarCalidad;
import ar.edu.unahur.obj2.parcial2.Consola.ConsolaReproduccion;
import ar.edu.unahur.obj2.parcial2.Consola.Estado;
import ar.edu.unahur.obj2.parcial2.Consola.Pause;
import ar.edu.unahur.obj2.parcial2.Consola.Play;
import ar.edu.unahur.obj2.parcial2.Consola.Reproductor;
import ar.edu.unahur.obj2.parcial2.Consola.Skip;
import ar.edu.unahur.obj2.parcial2.catalogo.Album;
import ar.edu.unahur.obj2.parcial2.catalogo.Cancion;
import ar.edu.unahur.obj2.parcial2.excepciones.CalidadNoPermitidaException;
import ar.edu.unahur.obj2.parcial2.excepciones.ColaVaciaException;
import ar.edu.unahur.obj2.parcial2.excepciones.ContenidoNoDisponibleException;
import ar.edu.unahur.obj2.parcial2.excepciones.NoAdmiteDeshacerException;
import ar.edu.unahur.obj2.parcial2.excepciones.SinAccionesParaDeshacerException;
import ar.edu.unahur.obj2.parcial2.planes.Calidad;
import ar.edu.unahur.obj2.parcial2.planes.PlanBase;
import ar.edu.unahur.obj2.parcial2.usuario.Usuario;

public class ConsolaCoberturaTotalTest {

    private Usuario usuario;
    private Reproductor reproductor;
    private ConsolaReproduccion consola;
    private Cancion cancionNormal;
    private Cancion cancionPremium;
    private Album album;

    @BeforeEach
    void setUp() {
        usuario = new Usuario("Kevin", new PlanBase());
        reproductor = usuario.getReproductor();
        consola = new ConsolaReproduccion(reproductor);

        cancionNormal = new Cancion(1, "De Música Ligera", 210, "Soda Stereo", "Rock", false, 10);
        cancionPremium = new Cancion(2, "Persiana Americana", 290, "Soda Stereo", "Rock", true, 5);

        album = new Album(3, "Signos", "Soda Stereo");
        album.agregarCancion(cancionNormal);
    }

    @Test
    void dadoUnReproductor_CuandoReproduceUnaPista_EntoncesEstableceLaPistaActual() {
        reproductor.reproducir(cancionNormal);
        Assertions.assertEquals(cancionNormal, reproductor.getActual());
    }

    @Test
    void dadoUnReproductor_CuandoReproduceUnaPista_EntoncesCambiaSuEstadoAReproduciendo() {
        reproductor.reproducir(cancionNormal);
        Assertions.assertEquals(Estado.REPRODUCIENDO, reproductor.getEstado());
    }

    @Test
    void dadoUnReproductor_CuandoReproduceUnaPista_EntoncesLaAgregaAlHistorialDelUsuario() {
        reproductor.reproducir(cancionNormal);
        Assertions.assertEquals(1, usuario.getHistorial().size());
    }

    @Test
    void dadoUnReproductor_CuandoReproduceUnaCancion_EntoncesIncrementaSusReproducciones() {
        reproductor.reproducir(cancionNormal);
        Assertions.assertEquals(11, cancionNormal.getCantidadReproducciones());
    }

    @Test
    void dadoUnReproductorConPlanBase_CuandoIntentaReproducirPistaPremium_EntoncesLanzaContenidoNoDisponibleException() {
        Assertions.assertThrows(ContenidoNoDisponibleException.class, () -> reproductor.reproducir(cancionPremium));
    }

    @Test
    void dadoUnReproductorEnReproduccion_CuandoSePausa_EntoncesPasaAEstadoPausado() {
        reproductor.reproducir(cancionNormal);
        reproductor.pausar();
        Assertions.assertEquals(Estado.PAUSADO, reproductor.getEstado());
    }

    @Test
    void dadoUnReproductorDetenido_CuandoSePausa_EntoncesPermaneceDetenido() {
        reproductor.pausar();
        Assertions.assertEquals(Estado.DETENIDO, reproductor.getEstado());
    }

    @Test
    void dadoUnReproductor_CuandoCambiaACalidadPermitida_EntoncesActualizaLaCalidad() {
        reproductor.cambiarCalidad(Calidad.ESTANDAR);
        Assertions.assertEquals(Calidad.ESTANDAR, reproductor.getCalidad());
    }

    @Test
    void dadoUnReproductorConPlanBase_CuandoIntentaCambiarACalidadAlta_EntoncesLanzaCalidadNoPermitidaException() {
        Assertions.assertThrows(CalidadNoPermitidaException.class, () -> reproductor.cambiarCalidad(Calidad.ALTA));
    }

    @Test
    void dadoUnReproductorConColaVacia_CuandoSeConsultaLaPrimeraDeLaCola_EntoncesLanzaColaVaciaException() {
        Assertions.assertThrows(ColaVaciaException.class, () -> reproductor.primeraDeCola());
    }

    @Test
    void dadoUnReproductorConElementosEnCola_CuandoQuitaElPrimero_EntoncesLaColaQuedaVacia() {
        reproductor.encolar(List.of(cancionNormal));
        reproductor.quitarPrimeraDeCola();
        Assertions.assertTrue(reproductor.getCola().isEmpty());
    }

    @Test
    void dadoUnReproductor_CuandoPoneUnaPistaAlFrente_EntoncesPasaASerLaPrimeraDeLaCola() {
        reproductor.ponerAlFrente(cancionNormal);
        Assertions.assertEquals(cancionNormal, reproductor.primeraDeCola());
    }

    @Test
    void dadoUnReproductorConPistas_CuandoDesencolaLasUltimas_EntoncesLasRemueveDeLaCola() {
        reproductor.encolar(List.of(cancionNormal));
        reproductor.desencolarUltimas(List.of(cancionNormal));
        Assertions.assertTrue(reproductor.getCola().isEmpty());
    }

    @Test
    void dadoUnReproductorConReproduccion_CuandoRevierteLaReproduccion_EntoncesRestauraLasReproducciones() {
        reproductor.reproducir(cancionNormal);
        reproductor.revertirReproduccion(cancionNormal, null, Estado.DETENIDO);
        Assertions.assertEquals(10, cancionNormal.getCantidadReproducciones());
    }

    @Test
    void dadaUnaConsolaConAccionesEncoladas_CuandoEjecutaElLote_EntoncesCambiaElEstadoDelReproductor() {
        consola.encolar(new Play(cancionNormal));
        consola.ejecutarLote();
        Assertions.assertEquals(Estado.REPRODUCIENDO, reproductor.getEstado());
    }

    @Test
    void dadaUnaConsolaConAccionFallida_CuandoEjecutaElLote_EntoncesRegistraLaAnomalia() {
        consola.encolar(new Play(cancionPremium));
        consola.ejecutarLote();
        Assertions.assertEquals(1, consola.getAnomalias().size());
    }

    @Test
    void dadaUnaConsolaConAccionEjecutada_CuandoDeshaceLaUltima_EntoncesRevierteElEfectoEnLaCola() {
        consola.encolar(new AddToQueue(album));
        consola.ejecutarLote();
        consola.deshacerUltimo();
        Assertions.assertTrue(reproductor.getCola().isEmpty());
    }

    @Test
    void dadaUnaConsolaSinHistorial_CuandoSeIntentaDeshacer_EntoncesLanzaSinAccionesParaDeshacerException() {
        Assertions.assertThrows(SinAccionesParaDeshacerException.class, () -> consola.deshacerUltimo());
    }

    @Test
    void dadaUnaAnomalia_CuandoSeConsultaSuAccion_EntoncesDevuelveLaAccionOriginal() {
        Accion accion = new Play(cancionPremium);
        Anomalia anomalia = new Anomalia(accion, "Error");
        Assertions.assertEquals(accion, anomalia.getAccion());
    }

    @Test
    void dadaUnaAnomalia_CuandoSeConsultaSuMotivo_EntoncesDevuelveElMensajeDeError() {
        Anomalia anomalia = new Anomalia(new Play(cancionPremium), "Error");
        Assertions.assertEquals("Error", anomalia.getMotivo());
    }

    @Test
    void dadaUnaAccionSkip_CuandoSeConsultaSiAdmiteDeshacer_EntoncesDevuelveVerdadero() {
        Skip skip = new Skip();
        Assertions.assertTrue(skip.admiteDeshacer());
    }

    @Test
    void dadaUnaAccionAddToQueue_CuandoSeConsultaSiAdmiteDeshacer_EntoncesDevuelveVerdadero() {
        AddToQueue add = new AddToQueue(album);
        Assertions.assertTrue(add.admiteDeshacer());
    }

    @Test
    void dadaUnaAccionCambiarCalidad_CuandoSeConsultaSiAdmiteDeshacer_EntoncesDevuelveVerdadero() {
        CambiarCalidad cambiar = new CambiarCalidad(Calidad.ALTA);
        Assertions.assertTrue(cambiar.admiteDeshacer());
    }

    @Test
    void dadaUnaAccionCambiarCalidad_CuandoSeCorrigeYEjecuta_EntoncesAplicaLaNuevaCalidad() {
        CambiarCalidad cambiar = new CambiarCalidad(Calidad.ALTA);
        cambiar.corregir(Calidad.ESTANDAR);
        cambiar.ejecutar(reproductor);
        Assertions.assertEquals(Calidad.ESTANDAR, reproductor.getCalidad());
    }

    @Test
    void dadaUnaAccionPause_CuandoSeConsultaSiAdmiteDeshacer_EntoncesDevuelveFalso() {
        Pause pause = new Pause();
        Assertions.assertFalse(pause.admiteDeshacer());
    }

    @Test
    void dadaUnaAccionQueNoAdmiteDeshacer_CuandoSeIntentaDeshacer_EntoncesLanzaNoAdmiteDeshacerException() {
        Pause pause = new Pause();
        Assertions.assertThrows(NoAdmiteDeshacerException.class, () -> pause.deshacer(reproductor));
    }

    @Test
    void dadoUnReproductorConCola_CuandoSkipSeEjecutaDirectamente_EntoncesEstableceLaPistaComoActual() {
        reproductor.encolar(List.of(cancionNormal));
        Skip skip = new Skip();
        
        skip.ejecutar(reproductor);

        Assertions.assertEquals(cancionNormal, reproductor.getActual());
    }

    @Test
    void dadoUnSkipEjecutado_CuandoSeDeshaceDirectamente_EntoncesRestauraElContadorDeReproducciones() {
        reproductor.encolar(List.of(cancionNormal));
        Skip skip = new Skip();
        skip.ejecutar(reproductor);

        skip.deshacer(reproductor);

        Assertions.assertEquals(10, cancionNormal.getCantidadReproducciones());
    }
}