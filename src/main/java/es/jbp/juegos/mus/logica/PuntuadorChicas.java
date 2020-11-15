package es.jbp.juegos.mus.logica;

import es.jbp.juegos.mus.persistencia.Jugada;
import es.jbp.juegos.mus.persistencia.Naipe;


/**
 *
 * @author jorge
 */
public class PuntuadorChicas extends PuntuadorGrandes {

    @Override
    public int calcularPuntuacion(Jugada jugada) {        
        if (jugada == null || jugada.getCartas() == null) {
            return 0;
        }
        int puntuacion = 0;
        int factor = 1;
        for (Naipe carta : jugada.getCartas()) {
            puntuacion = puntuacion + calcularPuntuacion(carta) * factor;
            factor *= 10;
        }
        return puntuacion;
    }    
}
