package ar.edu.unahur.obj2.catering.modelo.adicionales;
import ar.edu.unahur.obj2.catering.modelo.Propuesta;

public class VajillaPremium extends AdicionalDecorator {
    private static final double COSTO_VAJILLA = 12000.0;

    public VajillaPremium(Propuesta propuestaBase) {
        super(propuestaBase);
    }

    @Override
    public double getPrecio() {
        return propuestaBase.getPrecio() + COSTO_VAJILLA;
    }

    @Override
    public String getDescripcion() {
        return propuestaBase.getDescripcion() + " + Vajilla premium";
    }
}