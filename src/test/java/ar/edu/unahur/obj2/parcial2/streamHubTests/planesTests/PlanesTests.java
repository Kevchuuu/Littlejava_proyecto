package ar.edu.unahur.obj2.parcial2.streamHubTests.planesTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import ar.edu.unahur.obj2.parcial2.planes.AltaCalidad;
import ar.edu.unahur.obj2.parcial2.planes.Calidad;
import ar.edu.unahur.obj2.parcial2.planes.Configuracion;
import ar.edu.unahur.obj2.parcial2.planes.DescargasOffline;
import ar.edu.unahur.obj2.parcial2.planes.Lossless;
import ar.edu.unahur.obj2.parcial2.planes.PlanBase;
import ar.edu.unahur.obj2.parcial2.planes.PlanSuscripcion;
import ar.edu.unahur.obj2.parcial2.planes.SinPublicidad;

public class PlanesTests {
    @Test 
    void dadoLosNivelesDeCalidadCuandoCons7ultoSusBitrat5eEntoncesObtengoLosValoresCorrectos() {
        Calidad estandar = Calidad.ESTANDAR;
        Calidad alta = Calidad.ALTA;
        Calidad lossless = Calidad.LOSSLESS;

        Integer bitrateEstandar = estandar.getBitrate();
        Integer bitrateAlta = alta.getBitrate();
        Integer bitrateLossLess = lossless.getBitrate();

        assertEquals(128, bitrateEstandar);
        assertEquals(320, bitrateAlta);
        assertEquals(1411, bitrateLossLess);
    }

    @Test 
    void dadoDosNivelesDeCalidadCuandoComparoCualEsMayorEntoncesObtengoLaCalidadMasAlta() {
        Calidad estandar = Calidad.ESTANDAR;
        Calidad lossless = Calidad.LOSSLESS;

        Calidad resultado = estandar.mayor(lossless);

        assertEquals(Calidad.LOSSLESS, resultado);
    }

    @Test 
    void dadoDosNivelesDeCalidadCuandoElPrimeroEsMayorEntoncesSeConservaElPrimero() {
        Calidad lossless = Calidad.LOSSLESS;
        Calidad alta = Calidad.ALTA;

        Calidad resultado = lossless.mayor(alta);

        assertEquals(Calidad.LOSSLESS, resultado);
    }

    @Test 
    void dadoUnPlanBaseCuandoConsultoSusCaracteristicasEntoncesDevuelveLaConfiguracionInicial() {
        PlanSuscripcion plan = new PlanBase();

        Integer costo = plan.costoMensual();
        Calidad calidad = plan.calidadMaxima();
        Boolean premium = plan.permiteContenidoPremium();
        Boolean descargas = plan.permiteDescargas();
        Boolean publicidad = plan.tienePublicidad();

        assertEquals(1000, costo);
        assertEquals(Calidad.ESTANDAR, calidad);
        assertFalse(premium);
        assertFalse(descargas);
        assertTrue(publicidad);
    }

    @Test 
    void dadoUnPlanBaseCuandoAgregoAltaCalidadEntoncesAumentaElCostoYHabilitaPremium() {
        PlanSuscripcion plan = new PlanBase();
        
        PlanSuscripcion planConAltaCalidad = new AltaCalidad(plan);

        assertEquals(1300, planConAltaCalidad.costoMensual());
        assertEquals(Calidad.ALTA, planConAltaCalidad.calidadMaxima());
        assertTrue(planConAltaCalidad.permiteContenidoPremium());
    }

    @Test
    void dadoUnPlnanConAltaCalidadC7uandoConsultoOtrasCapacidadesEntoncesMantieneLasIniciales() {
        PlanSuscripcion plan = new AltaCalidad(new PlanBase());

        Boolean descargas = plan.permiteDescargas();
        Boolean publicidad = plan.tienePublicidad();

        assertFalse(descargas);
        assertTrue(publicidad);
    }

    @Test 
    void dadoLaConfiguracionCuandoConsultoLosCostosEntoncesDevuelveLosValoresDefinidos() {
        Integer costoBase = Configuracion.COSTO_PLAN_BASE;
        Integer costoAlta = Configuracion.COSTO_ALTA_CALIDAD;
        Integer costoLossLess = Configuracion.COSTO_LOSSLESS;
        Integer costoDescargas = Configuracion.COSTO_DESCARGAS;
        Integer costoSinPublicidad = Configuracion.COSTO_SIN_PUBLICIDAD;

        assertEquals(1000, costoBase);
        assertEquals(300, costoAlta);
        assertEquals(600, costoLossLess);
        assertEquals(200, costoDescargas);
        assertEquals(500, costoSinPublicidad);
    }
    
    @Test
    public void testPlanConDecoradoresIndependientementeDelOrden() {
        PlanSuscripcion plan1 = new PlanBase();
        plan1 = new AltaCalidad(plan1);
        plan1 = new Lossless(plan1);
        plan1 = new SinPublicidad(plan1);

        PlanSuscripcion plan2 = new PlanBase();
        plan2 = new SinPublicidad(plan2);
        plan2 = new Lossless(plan2);
        plan2 = new AltaCalidad(plan2);

        assertEquals(2400, plan1.costoMensual(), 0.01);
        assertEquals(plan1.costoMensual(), plan2.costoMensual(), 0.01);

        assertEquals(Calidad.LOSSLESS, plan1.calidadMaxima());
        assertEquals(plan1.calidadMaxima(), plan2.calidadMaxima());

        assertTrue(plan1.permiteContenidoPremium());
        assertTrue(plan2.permiteContenidoPremium());

        assertFalse(plan1.permiteDescargas());
        assertFalse(plan2.permiteDescargas());

        assertFalse(plan1.tienePublicidad());
        assertFalse(plan2.tienePublicidad());
    }

}
