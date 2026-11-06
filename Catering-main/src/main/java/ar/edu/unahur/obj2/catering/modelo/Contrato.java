package ar.edu.unahur.obj2.catering.modelo;

public class Contrato {
    private final String cliente;
    private final double importe;
    private final String descripcion;

    public Contrato(String cliente, Propuesta propuesta) {
        this.cliente = cliente;
        // Congela el importe y la descripción vigentes en este instante
        this.importe = propuesta.getPrecio();
        this.descripcion = propuesta.getDescripcion();
    }

    public String getCliente() {
        return cliente;
    }

    public double getImporte() {
        return importe;
    }

    public String getDescripcion() {
        return descripcion;
    }
}