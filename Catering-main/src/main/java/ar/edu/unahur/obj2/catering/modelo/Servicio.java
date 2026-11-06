package ar.edu.unahur.obj2.catering.modelo;
import ar.edu.unahur.obj2.catering.excepciones.PrecioNegativoException;

public class Servicio implements Propuesta {
    private final String nombre;
    private double precio;

    public Servicio(String nombre, double precio) {
        if (precio < 0) {
            throw new PrecioNegativoException("El precio no puede ser negativo: " + precio);
        }
        this.nombre = nombre;
        this.precio = precio;
    }

    @Override
    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        if (precio < 0) {
            throw new PrecioNegativoException("El precio no puede ser negativo: " + precio);
        }
        this.precio = precio;
    }

    @Override
    public String getDescripcion() {
        return nombre;
    }

    @Override
    public int getCantidadServicios() {
        return 1;
    }

    @Override
    public boolean incluyeServicio(String nombreServicio) {
        return this.nombre.equalsIgnoreCase(nombreServicio);
    }
}