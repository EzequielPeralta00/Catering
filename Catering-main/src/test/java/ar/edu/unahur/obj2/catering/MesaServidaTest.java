package ar.edu.unahur.obj2.catering;

import ar.edu.unahur.obj2.catering.excepciones.DescuentoInvalidoException;
import ar.edu.unahur.obj2.catering.excepciones.PrecioNegativoException;
import ar.edu.unahur.obj2.catering.modelo.*;
import ar.edu.unahur.obj2.catering.modelo.adicionales.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MesaServidaTest {

    private Servicio entradas;
    private Servicio parrillada;
    private Servicio dulce;
    private Servicio tragos;
    private Servicio dj;
    private Servicio brindis;

    private Menu menuParrillero;
    private Menu packFiesta;
    private Menu eventoCompleto;

    @BeforeEach
    void setUp() {
        // catalogo inicial
        entradas = new Servicio("Mesa de entradas", 80000);
        parrillada = new Servicio("Parrillada completa", 240000);
        dulce = new Servicio("Mesa dulce", 60000);
        tragos = new Servicio("Barra de tragos", 150000);
        dj = new Servicio("DJ y sonido", 120000);
        brindis = new Servicio("Brindis de bienvenida", 50000);

        menuParrillero = new Menu("Menú Parrillero", 10);
        menuParrillero.agregarComponente(entradas);
        menuParrillero.agregarComponente(parrillada);
        menuParrillero.agregarComponente(dulce);

        packFiesta = new Menu("Pack Fiesta", 5);
        packFiesta.agregarComponente(tragos);
        packFiesta.agregarComponente(dj);

        eventoCompleto = new Menu("Evento Completo", 20);
        eventoCompleto.agregarComponente(menuParrillero);
        eventoCompleto.agregarComponente(packFiesta);
        eventoCompleto.agregarComponente(brindis);
    }

    // --- ETAPA 1 ---

    @Test
    void dadoUnServicioConPrecioNegativo_alCrearlo_lanzaPrecioNegativoException() {
        assertThrows(PrecioNegativoException.class, () -> new Servicio("Servicio inválido", -500));
    }

    @Test
    void dadoUnMenuConDescuentoMayorACien_alCrearlo_lanzaDescuentoInvalidoException() {
        assertThrows(DescuentoInvalidoException.class, () -> new Menu("Menú inválido", 110));
    }

    @Test
    void dadoElCatalogoInicial_alConsultarPreciosYCantidades_calculaCorrectamente() {
        assertEquals(342000, menuParrillero.getPrecio());
        assertEquals(256500, packFiesta.getPrecio());
        assertEquals(3, menuParrillero.getCantidadServicios());
        assertTrue(menuParrillero.incluyeServicio("Parrillada completa"));
        assertFalse(menuParrillero.incluyeServicio("DJ y sonido"));
    }

    @Test
    void dadoUnMenuQueContieneOtrosMenus_alContarServicios_sumaLosDeTodosLosNiveles() {
        assertEquals(518800, eventoCompleto.getPrecio());
        assertEquals(6, eventoCompleto.getCantidadServicios());
        assertTrue(eventoCompleto.incluyeServicio("DJ y sonido"));
        assertFalse(eventoCompleto.incluyeServicio("Fotógrafo"));
    }

    // --- ETAPA 2 ---

    @Test
    void dadoUnServicioNocturnoAplicadoAntesQueMozos_alCalcularPrecio_elOrdenAlteraElPrecioFinal() {
        Propuesta opc1 = new ServicioNocturno(new MozosAdicionales(menuParrillero));
        assertEquals(432000, opc1.getPrecio());
        assertEquals("Menú Parrillero + Mozos adicionales + Servicio nocturno", opc1.getDescripcion());

        Propuesta opc2 = new MozosAdicionales(new ServicioNocturno(menuParrillero));
        assertEquals(428400, opc2.getPrecio());
        assertEquals("Menú Parrillero + Servicio nocturno + Mozos adicionales", opc2.getDescripcion());
    }

    @Test
    void dadasVariasCombinacionesDeAdicionales_alConsultar_calculaCorrectamente() {
        Propuesta brindisDobleVajilla = new VajillaPremium(new VajillaPremium(brindis));
        assertEquals(74000, brindisDobleVajilla.getPrecio());

        Propuesta dulceDobleNocturno = new ServicioNocturno(new ServicioNocturno(dulce));
        assertEquals(86400, dulceDobleNocturno.getPrecio());

        Propuesta eventoVajilla = new VajillaPremium(eventoCompleto);
        assertEquals(530800, eventoVajilla.getPrecio());
    }

    @Test
    void dadoUnContratoCreado_alActualizarElPrecioDeUnServicio_elImporteContratadoNoCambia() {
        Propuesta propuestaContratada = new MozosAdicionales(menuParrillero);
        Contrato contratoLucia = new Contrato("Lucía", propuestaContratada);

        assertEquals(360000, contratoLucia.getImporte());
        assertEquals("Menú Parrillero + Mozos adicionales", contratoLucia.getDescripcion());

        // Si cambia el precio base de la parrillada despues del contrato
        parrillada.setPrecio(260000);

        // El menu cambia de precio, pero el contrato guardado en Lucia sigue intacto
        assertEquals(378000, new MozosAdicionales(menuParrillero).getPrecio());
        assertEquals(360000, contratoLucia.getImporte());
    }
}