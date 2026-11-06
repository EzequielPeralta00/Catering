package ar.edu.unahur.obj2.catering.modelo.adicionales;
import ar.edu.unahur.obj2.catering.modelo.Propuesta;

public class MozosAdicionales extends AdicionalDecorator {
    private static final double COSTO_MOZOS = 18000.0;

    public MozosAdicionales(Propuesta propuestaBase) {
        super(propuestaBase);
    }

    @Override
    public double getPrecio() {
        return propuestaBase.getPrecio() + COSTO_MOZOS;
    }

    @Override
    public String getDescripcion() {
        return propuestaBase.getDescripcion() + " + Mozos adicionales";
    }
}