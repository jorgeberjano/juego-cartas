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
public enum EstadoLance {
    DECIDIR_MUS(DAR_MUS, CORTAR),
    DESCARTE(DESCARTAR),
    APUESTA(PASAR, ENVIDAR, LANZAR_ORDAGO),
    RESPUESTA(QUERER, NO_QUERER, ENVIDAR, LANZAR_ORDAGO);
    
    private List<Accion> acciones;
    
    private EstadoLance(Accion... acciones) {
        this.acciones = Arrays.asList(acciones);
    }

    public List<Accion> getAcciones() {
        return acciones;
    }
}
