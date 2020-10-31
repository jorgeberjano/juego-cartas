package es.jbp.juegos.mus.puntuadores;

import es.jbp.juegos.mus.Jugada;

/**
 *
 * @author jorge
 */
public class PuntuadorChicas extends PuntuadorGrandes {

    @Override
    public int calcularPuntuacion(Jugada jugada) {        
        return -super.calcularPuntuacion(jugada);
    }    
}
