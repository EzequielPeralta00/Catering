package ar.edu.unahur.obj2.catering.modelo;
import ar.edu.unahur.obj2.catering.excepciones.DescuentoInvalidoException;
import ar.edu.unahur.obj2.catering.excepciones.PropuestaNoIncluidaException;
import java.util.*;

public class Menu implements Propuesta {
    private final String nombre;
    private final double descuento; // Porcentaje entre 0 y 100
    private final List<Propuesta> componentes;

    public Menu(String nombre, double descuento) {
        if (descuento < 0 || descuento > 100) {
            throw new DescuentoInvalidoException("El descuento debe estar entre 0 y 100: " + descuento);
        }
        this.nombre = nombre;
        this.descuento = descuento;
        this.componentes = new ArrayList<>();
    }

    public void agregarComponente(Propuesta propuesta) {
        this.componentes.add(propuesta);
    }

    public void quitarComponente(Propuesta propuesta) {
        if (!this.componentes.contains(propuesta)) {
            throw new PropuestaNoIncluidaException(
                "La propuesta '" + propuesta.getDescripcion() + "' no es un componente directo de " + nombre
            );
        }
        this.componentes.remove(propuesta);
    }

    public List<Propuesta> getComponentes() {
        return Collections.unmodifiableList(componentes);
    }

    @Override
    public double getPrecio() {
        double sumaPrecios = componentes.stream()
                .mapToDouble(Propuesta::getPrecio)
                .sum();
        return sumaPrecios * (1.0 - (descuento / 100.0));
    }

    @Override
    public String getDescripcion() {
        return nombre;
    }

    @Override
    public int getCantidadServicios() {
        return componentes.stream()
                .mapToInt(Propuesta::getCantidadServicios)
                .sum();
    }

    @Override
    public boolean incluyeServicio(String nombreServicio) {
        return componentes.stream()
                .anyMatch(c -> c.incluyeServicio(nombreServicio));
    }
}
