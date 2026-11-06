package ar.edu.unahur.obj2.catering;

import ar.edu.unahur.obj2.catering.excepciones.DescuentoInvalidoException;
import ar.edu.unahur.obj2.catering.excepciones.PrecioNegativoException;
import ar.edu.unahur.obj2.catering.modelo.Menu;
import ar.edu.unahur.obj2.catering.modelo.Servicio;

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
        // servicios del catalogo inicial
        entradas = new Servicio("Mesa de entradas", 80000);
        parrillada = new Servicio("Parrillada completa", 240000);
        dulce = new Servicio("Mesa dulce", 60000);
        tragos = new Servicio("Barra de tragos", 150000);
        dj = new Servicio("DJ y sonido", 120000);
        brindis = new Servicio("Brindis de bienvenida", 50000);

        // menu simples
        menuParrillero = new Menu("Menú Parrillero", 10);
        menuParrillero.agregarComponente(entradas);
        menuParrillero.agregarComponente(parrillada);
        menuParrillero.agregarComponente(dulce);

        packFiesta = new Menu("Pack Fiesta", 5);
        packFiesta.agregarComponente(tragos);
        packFiesta.agregarComponente(dj);

        // menu anidado
        eventoCompleto = new Menu("Evento Completo", 20);
        eventoCompleto.agregarComponente(menuParrillero);
        eventoCompleto.agregarComponente(packFiesta);
        eventoCompleto.agregarComponente(brindis);
    }

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
}