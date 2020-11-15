package es.jbp.juegos.mus.algoritmo;

import es.jbp.juegos.mus.enumerados.Accion;
import es.jbp.juegos.mus.enumerados.AnimoJugador;
import static es.jbp.juegos.mus.enumerados.AnimoJugador.DESESPERADO;
import static es.jbp.juegos.mus.enumerados.AnimoJugador.GANANDO;
import static es.jbp.juegos.mus.enumerados.AnimoJugador.PERDIENDO;
import es.jbp.juegos.mus.enumerados.Lance;
import es.jbp.juegos.mus.persistencia.Jugada;
import java.util.List;

/**
 *
 * @author jorge
 */
public class DecisorCortar implements Decisor {
    
    private int cantidadDecisionFinal;

    public void setSituacion(Situacion situacion) {
        Lance lance = situacion.getLance();
        Jugada jugada = situacion.getJugada();
        AnimoJugador animoJugador = situacion.getAnimoJugador();
        int orden = situacion.getOrden();        
        
        decidirSegunAnimo(animoJugador);
        decidirSegunJugada(jugada);
        decidirSegunOrden(orden);
    }
    
    
    public void decidirSegunAnimo(AnimoJugador animoJugador) {
        Decision decision;
        if (animoJugador == null) {
            decision = Decision.NO_SE;
        } else switch (animoJugador) {
            case DESESPERADO:
                decision = Decision.SEGURAMENTE_SI;            
            case PERDIENDO:
                decision = Decision.PUEDE_QUE_SI;
            case GANANDO:
                decision = Decision.PUEDE_QUE_NO;
            default:
                decision = Decision.NO_SE;
        }
        cantidadDecisionFinal +=  decision.getCantidad();
    }

    public void decidirSegunJugada(Jugada jugada) {        
        if (jugada == null) {
            return;
        }
        
        int n = 0;
        if (jugada.tiene31()) {
            n += 3;
        } else if (jugada.tiene32()) {
            n += 2;
        } else if (jugada.tieneJuego()) {
            n += 1;
        }        
        if (jugada.tieneDuples()) {
            n += 3;
        } else if (jugada.tieneMedia()) {
            n += 2;
        } else if (jugada.tienePares()) {
            n += 1;
        }       
        
        Decision decision = Decision.NO_SE;
        if (n >= 5) {
            decision = Decision.POR_SUPUESTO_QUE_SI;
        } else if (n == 3) {
            decision = Decision.PUEDE_QUE_SI;
        } else if (n == 2) {
            decision = Decision.PUEDE_QUE_NO;
        } else if (n == 1) {
            decision = Decision.SEGURAMENTE_NO;
        } else if (n == 0) {
            decision = Decision.POR_SUPUESTO_QUE_NO;
        }       
        cantidadDecisionFinal += decision.getCantidad();
    }

    public void decidirSegunOrden(int orden) {
        Decision decision = Decision.NO_SE;
        switch (orden) {
            case 0:
                decision = Decision.PUEDE_QUE_NO;
                break;
            case 1:
                decision = Decision.PUEDE_QUE_SI;
                break;
            case 2:
                decision = Decision.SEGURAMENTE_NO;
                break;
            case 3:
                decision = Decision.PUEDE_QUE_SI;
                break;
            default: decision = Decision.NO_SE;
        }
        cantidadDecisionFinal += decision.getCantidad();
    }
    
    public void decidirSegunDescartes(int numeroDescartes) {
        Decision decision = Decision.NO_SE;    
        if (numeroDescartes > 2) {
            decision = Decision.SEGURAMENTE_SI;
        } else if (numeroDescartes > 1) {
            decision = Decision.PUEDE_QUE_SI;
        } else if (numeroDescartes > 0) {
            decision = Decision.PUEDE_QUE_NO;
        } else {
            decision = Decision.SEGURAMENTE_NO;        
        }
        cantidadDecisionFinal += decision.getCantidad();
    }

    public Accion getDecision() {        
        return cantidadDecisionFinal >= 0 ? Accion.CORTAR : Accion.DAR_MUS;
    }

    @Override
    public int getApuesta() {
        return 0;
    }

    @Override
    public List<Integer> getDescartes() {
        return null;
    }
}
