package es.jbp.juegos.mus.algoritmo;

import es.jbp.juegos.mus.enumerados.Accion;
import es.jbp.juegos.mus.enumerados.AnimoJugador;
import static es.jbp.juegos.mus.enumerados.AnimoJugador.DESESPERADO;
import static es.jbp.juegos.mus.enumerados.AnimoJugador.GANANDO;
import static es.jbp.juegos.mus.enumerados.AnimoJugador.PERDIENDO;
import es.jbp.juegos.mus.enumerados.Lance;
import static es.jbp.juegos.mus.enumerados.Lance.CHICAS;
import static es.jbp.juegos.mus.enumerados.Lance.GRANDES;
import static es.jbp.juegos.mus.enumerados.Lance.JUEGO;
import static es.jbp.juegos.mus.enumerados.Lance.PARES;
import es.jbp.juegos.mus.persistencia.Jugada;
import java.util.List;
import java.util.Random;

/**
 *
 * @author jorge
 */
public class DecisorApuesta implements Decisor {
    
    private int apuesta = 2;
    private Accion accion;
    private final Random random = new Random();
                
    @Override
    public void setSituacion(Situacion situacion) {    
        Lance lance = situacion.getLance();
        Jugada jugada = situacion.getJugada();
        AnimoJugador animoJugador = situacion.getAnimoJugador();
        int orden = situacion.getOrden();       
        int numeroDescartes = situacion.getNumeroDescartes();
        
        Decision decisionJugada;
        switch (lance) {
            case GRANDES:
                decisionJugada = decidirSegunGrandes(jugada.valorNumericoGrandes());
                break;                
            case CHICAS:
                decisionJugada = decidirSegunChicas(jugada.valorNumericoChicas());
                break;
            case PARES:
                decisionJugada = decidirSegunPares(jugada);
                break;
            case JUEGO:
                decisionJugada = decidirSegunJuego(jugada);
                break;
            case PUNTO:
                decisionJugada = decidirSegunPunto(jugada);
                break;
            default:
                decisionJugada = Decision.NO_SE;                

        }
        Decision decisionOrden = decidirSegunOrden(orden);
        Decision decisionAnimo = decidirSegunAnimo(animoJugador);
        Decision decisionDescartes = decidirSegunDescartes(numeroDescartes);
        
        if (animoJugador == DESESPERADO && lance != CHICAS) {
            accion = Accion.LANZAR_ORDAGO;
            return;
        }
        
        int cantidad = decisionJugada.getCantidad() + decisionOrden.getCantidad() + decisionAnimo.getCantidad() + decisionDescartes.getCantidad();
        
        if (cantidad > 0) {
            accion = Accion.ENVIDAR;
            if (cantidad > 2) {
                apuesta += random.nextInt(8);
            }
        } else {
            accion = Accion.PASAR;
        }
    }    
    
    private Decision decidirSegunAnimo(AnimoJugador animoJugador) {
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
        return decision;
    }
    
    public Decision decidirSegunDescartes(int numeroDescartes) {
        Decision decision = Decision.NO_SE;    
        if (numeroDescartes > 2) {
            decision = Decision.SEGURAMENTE_NO;
        } else if (numeroDescartes > 1) {
            decision = Decision.PUEDE_QUE_NO;
        } else if (numeroDescartes > 0) {
            decision = Decision.PUEDE_QUE_SI;
        } else {
            decision = Decision.SEGURAMENTE_SI;
        }
        return decision;
    }
       
    private Decision decidirSegunGrandes(int n) {        
        Decision decision;
        if (n >= 9991) {
            decision = Decision.POR_SUPUESTO_QUE_SI;
        } else if (n >= 9971) {
            decision = Decision.SEGURAMENTE_SI;
        } else if (n >= 9911) {
            decision = Decision.PUEDE_QUE_SI;
        } else if (n >= 9811) {
            decision = Decision.NO_SE;
        } else if (n >= 9711) {
            decision = Decision.PUEDE_QUE_NO;
        } else if (n >= 9111) {
            decision = Decision.SEGURAMENTE_NO;
        } else {
            decision = Decision.POR_SUPUESTO_QUE_NO;
        }
        return decision;
    }
    
    private Decision decidirSegunChicas(int n) {        
        Decision decision;
        if (n <= 1114) {
            decision = Decision.POR_SUPUESTO_QUE_SI;
        } else if (n <= 1119) {
            decision = Decision.SEGURAMENTE_SI;
        } else if (n <= 1149) {
            decision = Decision.PUEDE_QUE_SI;
        } else if (n <= 1499) {
            decision = Decision.NO_SE;
        } else if (n <= 1799) {
            decision = Decision.PUEDE_QUE_NO;
        } else if (n <= 1999) {
            decision = Decision.SEGURAMENTE_NO;
        } else {
            decision = Decision.POR_SUPUESTO_QUE_NO;
        }
        return decision;
    }
    
    private Decision decidirSegunPares(Jugada jugada) {
        int n = jugada.valorNumericoPares();
        Decision decision;
        if (jugada.tieneDuples()) {
            if (n >= 9911) {
                decision = Decision.POR_SUPUESTO_QUE_SI;
            } else {
                decision = Decision.SEGURAMENTE_SI;
            }
        } else if (jugada.tieneMedia()) {
            decision = Decision.SEGURAMENTE_SI;
        } else if (n >= 9911) {            
            decision = Decision.SEGURAMENTE_NO;
        } else {
            decision = Decision.POR_SUPUESTO_QUE_NO;
        }
        return decision;
    }
       
    private Decision decidirSegunJuego(Jugada jugada) {
        if (jugada == null) {
            return Decision.POR_SUPUESTO_QUE_NO;
        }        
        int n = jugada.valorNumericoJuego();  
        if (jugada.tiene31()) {
            return Decision.POR_SUPUESTO_QUE_SI;
        } else if (jugada.tiene32()) {
            return Decision.SEGURAMENTE_SI;
        } else if (n >= 39) {
            return Decision.PUEDE_QUE_SI;
        } else if (n >= 37) {
            return Decision.PUEDE_QUE_NO;
        } else if (n >= 35) {
            return Decision.SEGURAMENTE_NO;
        } else {
            return Decision.POR_SUPUESTO_QUE_NO;
        } 
    }
    
    private Decision decidirSegunPunto(Jugada jugada) {
        if (jugada == null) {
            return Decision.POR_SUPUESTO_QUE_NO;
        }        
        int n = jugada.valorNumericoJuego();  
        if (n == 30) {
            return Decision.POR_SUPUESTO_QUE_SI;
        } else if (n == 29) {
            return Decision.SEGURAMENTE_SI;
        } else if (n >= 28) {
            return Decision.PUEDE_QUE_SI;
        } else if (n >= 27) {
            return Decision.PUEDE_QUE_NO;
        } else if (n >= 25) {
            return Decision.SEGURAMENTE_NO;
        } else {
            return Decision.POR_SUPUESTO_QUE_NO;
        } 
    }

    public Decision decidirSegunOrden(int orden) {
        Decision decision = Decision.NO_SE;
        switch (orden) {
            case 0:
                decision = Decision.PUEDE_QUE_SI;
                break;
            case 1:
                decision = Decision.PUEDE_QUE_NO;
                break;
            case 2:
                decision = Decision.SEGURAMENTE_NO;
                break;
            case 3:
                decision = Decision.SEGURAMENTE_NO;
                break;
            default:
                decision = Decision.NO_SE;
        }
        return decision;
    }
    
    public Decision decidirSegunHeCortado(boolean heCortado) {
        return heCortado ? Decision.SEGURAMENTE_SI : Decision.SEGURAMENTE_NO;
    }

    public Accion getDecision() {        
        return accion;
    }

    @Override
    public int getApuesta() {
        return apuesta;
    }

    @Override
    public List<Integer> getDescartes() {
        return null;
    }
}
