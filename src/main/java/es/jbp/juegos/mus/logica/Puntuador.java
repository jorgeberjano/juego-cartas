package es.jbp.juegos.mus.logica;

import es.jbp.juegos.mus.persistencia.Jugada;

/**
 *
 * @author jorge
 */
public interface Puntuador {
    
    int calcularPuntuacion(Jugada carta);
}
