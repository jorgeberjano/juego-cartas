package es.jbp.juegos.mus.algoritmo;

import es.jbp.juegos.mus.enumerados.Accion;
import java.util.List;

/**
 *
 * @author jorge
 */
public interface Decisor {

    void setSituacion(Situacion situacion);

    Accion getDecision();

    int getApuesta();

    List<Integer> getDescartes();
}
