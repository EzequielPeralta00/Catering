package ar.edu.unahur.obj2.catering.gestor;

import ar.edu.unahur.obj2.catering.excepciones.PrecioNegativoException;
import ar.edu.unahur.obj2.catering.modelo.Servicio;

public class ActualizarPrecioServicioModificacion implements Modificacion {
    private final Servicio servicio;
    private final double nuevoPrecio;
    private double precioAnterior;

    public ActualizarPrecioServicioModificacion(Servicio servicio, double nuevoPrecio) {
        if (nuevoPrecio < 0) {
            throw new PrecioNegativoException("El precio no puede ser negativo: " + nuevoPrecio);
        }
        this.servicio = servicio;
        this.nuevoPrecio = nuevoPrecio;
    }

    @Override
    public void aplicar() {
        // Se guarda el precio vigente antes de modificarlo para permitir 2 o mas actualizaciones seguidas del mismo servicio
        this.precioAnterior = servicio.getPrecio();
        servicio.setPrecio(nuevoPrecio);
    }

    @Override
    public void deshacer() {
        servicio.setPrecio(precioAnterior);
    }
}