package ar.edu.unahur.obj2.catering.excepciones;

public class PrecioNegativoException extends RuntimeException {
    public PrecioNegativoException(String mensaje) {
        super(mensaje);
    }
}