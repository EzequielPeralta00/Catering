package ar.edu.unahur.obj2.catering.gestor;

import ar.edu.unahur.obj2.catering.modelo.Menu;
import ar.edu.unahur.obj2.catering.modelo.Propuesta;

public class QuitarPropuestaModificacion implements Modificacion {
    private final Menu menu;
    private final Propuesta propuesta;

    public QuitarPropuestaModificacion(Menu menu, Propuesta propuesta) {
        this.menu = menu;
        this.propuesta = propuesta;
    }

    @Override
    public void aplicar() {
        menu.quitarComponente(propuesta);
    }

    @Override
    public void deshacer() {
        menu.agregarComponente(propuesta);
    }
}