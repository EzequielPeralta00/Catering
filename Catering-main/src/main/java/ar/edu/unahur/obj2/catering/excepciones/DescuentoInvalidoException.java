package ar.edu.unahur.obj2.catering.excepciones;

public class DescuentoInvalidoException extends RuntimeException {
    public DescuentoInvalidoException(String mensaje) {
        super(mensaje);
    }
}