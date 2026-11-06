package ar.edu.unahur.obj2.catering.modelo;

public interface Propuesta {
    double getPrecio();
    String getDescripcion();
    int getCantidadServicios();
    boolean incluyeServicio(String nombreServicio);
}
