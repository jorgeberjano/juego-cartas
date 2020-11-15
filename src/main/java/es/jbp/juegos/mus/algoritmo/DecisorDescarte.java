package es.jbp.juegos.mus.algoritmo;

import es.jbp.juegos.mus.enumerados.Accion;
import es.jbp.juegos.mus.enumerados.AnimoJugador;
import es.jbp.juegos.mus.enumerados.Lance;
import es.jbp.juegos.mus.persistencia.Jugada;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jorge
 */
public class DecisorDescarte implements Decisor {

    private List<Integer> descartes = new ArrayList<>();
    
    @Override
    public void setSituacion(Situacion situacion) {
        Jugada jugada = situacion.getJugada();
        AnimoJugador animoJugador = situacion.getAnimoJugador();
        //int orden = situacion.getOrden();
                
        if (jugada.tieneDuples()) {
            descartes.add(2);
            descartes.add(3);
        } else if (jugada.tieneMedia()) {
            int n0 = jugada.getCartas().get(0).getNumero();
            int n1 = jugada.getCartas().get(1).getNumero();
            if (n0 != n1) {
                descartes.add(0);
            } else {
                descartes.add(3);
            }
        } else {
            for (int i = 0; i < 4; i++) {
                int n = jugada.getCartas().get(i).getNumero();
                int nMinimo = animoJugador == AnimoJugador.DESESPERADO ? 7 : 8;
                if (n < nMinimo && n != 3) {
                    descartes.add(i);
                }
            }
            if (descartes.isEmpty()) {
                descartes.add(3);
            }
        }
    }    
   

    public Accion getDecision() {        
        return Accion.DESCARTAR;
    }

    @Override
    public int getApuesta() {
        return 0;
    }

    @Override
    public List<Integer> getDescartes() {
        return descartes;
    }


}
