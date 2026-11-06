package ar.edu.unahur.obj2.catering.gestor;

import ar.edu.unahur.obj2.catering.excepciones.NadaParaRevertirException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class GestorDeCarta {
    private final Deque<Modificacion> historial = new ArrayDeque<>();

    public void aplicar(Modificacion modificacion) {
        modificacion.aplicar();
        historial.push(modificacion);
    }

    public void revertir() {
        if (historial.isEmpty()) {
            throw new NadaParaRevertirException("El historial esta vacio, nada para revertir");
        }
        Modificacion ultima = historial.pop();
        ultima.deshacer();
    }

    public void aplicarLote(List<Modificacion> lote) {
        List<Modificacion> aplicadas = new ArrayList<>();
        try {
            for (Modificacion mod : lote) {
                mod.aplicar();
                aplicadas.add(mod);
            }
            // Si todo el lote se aplico sin fallas, se guardan las modificaciones en el historial global
            for (Modificacion mod : aplicadas) {
                historial.push(mod);
            }
        } catch (RuntimeException e) {
            // Rollback transaccional: deshacer en orden inverso las aplicadas del lote
            for (int i = aplicadas.size() - 1; i >= 0; i--) {
                aplicadas.get(i).deshacer();
            }
            throw e; // Se propaga la excepcion original sin registrar nada en el historial
        }
    }
}