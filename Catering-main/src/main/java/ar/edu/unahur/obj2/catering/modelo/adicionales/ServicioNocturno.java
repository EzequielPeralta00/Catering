package ar.edu.unahur.obj2.catering.modelo.adicionales;
import ar.edu.unahur.obj2.catering.modelo.Propuesta;

public class ServicioNocturno extends AdicionalDecorator {
    private static final double RECARGO_NOCTURNO = 0.20; // +20% del acumulado

    public ServicioNocturno(Propuesta propuestaBase) {
        super(propuestaBase);
    }

    @Override
    public double getPrecio() {
        return propuestaBase.getPrecio() * (1.0 + RECARGO_NOCTURNO);
    }

    @Override
    public String getDescripcion() {
        return propuestaBase.getDescripcion() + " + Servicio nocturno";
    }
}
