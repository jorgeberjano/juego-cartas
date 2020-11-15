package es.jbp.juegos.mus.enumerados;

import static es.jbp.juegos.mus.enumerados.Accion.CORTAR;
import static es.jbp.juegos.mus.enumerados.Accion.DAR_MUS;
import static es.jbp.juegos.mus.enumerados.Accion.DESCARTAR;
import static es.jbp.juegos.mus.enumerados.Accion.ENVIDAR;
import static es.jbp.juegos.mus.enumerados.Accion.LANZAR_ORDAGO;
import static es.jbp.juegos.mus.enumerados.Accion.NO_QUERER;
import static es.jbp.juegos.mus.enumerados.Accion.PASAR;
import static es.jbp.juegos.mus.enumerados.Accion.QUERER;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author jorge
 */
public enum FaseJuego {
    DESCARTE(DESCARTAR),
    DECIDIR_MUS(DAR_MUS, CORTAR),
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
