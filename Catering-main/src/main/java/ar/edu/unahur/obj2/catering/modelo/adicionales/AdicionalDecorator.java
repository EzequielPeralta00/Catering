package ar.edu.unahur.obj2.catering.modelo.adicionales;
import ar.edu.unahur.obj2.catering.modelo.Propuesta;

public abstract class AdicionalDecorator implements Propuesta {
    protected final Propuesta propuestaBase;

    public AdicionalDecorator(Propuesta propuestaBase) {
        if (propuestaBase == null) {
            throw new IllegalArgumentException("La propuesta base no puede ser nula");
        }
        this.propuestaBase = propuestaBase;
    }

    @Override
    public int getCantidadServicios() {
        return propuestaBase.getCantidadServicios();
    }

    @Override
    public boolean incluyeServicio(String nombreServicio) {
        return propuestaBase.incluyeServicio(nombreServicio);
    }
}