package es.jbp.juegos.mus;

import static es.jbp.juegos.mus.Accion.CORTAR;
import static es.jbp.juegos.mus.Accion.DAR_MUS;
import static es.jbp.juegos.mus.Accion.DESCARTAR;
import static es.jbp.juegos.mus.Accion.ENVIDAR;
import static es.jbp.juegos.mus.Accion.LANZAR_ORDAGO;
import static es.jbp.juegos.mus.Accion.NO_QUERER;
import static es.jbp.juegos.mus.Accion.PASAR;
import static es.jbp.juegos.mus.Accion.QUERER;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author jorge
 */
public enum FaseJuego {
    DECIDIR_MUS(DAR_MUS, CORTAR),
    DESCARTE(DESCARTAR),
    APUESTA(PASAR, ENVIDAR, LANZAR_ORDAGO),
    RESPUESTA(QUERER, NO_QUERER, ENVIDAR, LANZAR_ORDAGO),
    RESPUESTA_OTRO(QUERER, NO_QUERER, ENVIDAR, LANZAR_ORDAGO),
    RESPUESTA_ORDAGO(QUERER, NO_QUERER),
    RESPUESTA_ORDAGO_OTRO(QUERER, NO_QUERER);
    
    private List<Accion> acciones;
    
    private FaseJuego(Accion... acciones) {
        this.acciones = Arrays.asList(acciones);
    }

    public List<Accion> getAcciones() {
        return acciones;
    }
}
