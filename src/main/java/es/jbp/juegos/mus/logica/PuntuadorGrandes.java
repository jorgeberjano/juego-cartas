package es.jbp.juegos.mus.logica;

import es.jbp.juegos.mus.persistencia.Jugada;
import es.jbp.juegos.mus.persistencia.Naipe;

/**
 *
 * @author jorge
 */
public class PuntuadorGrandes implements Puntuador {
    
    @Override
    public int calcularPuntuacion(Jugada jugada) {        
        if (jugada == null || jugada.getCartas() == null) {
            return 0;
        }
        int puntuacion = 0;
        for (Naipe carta : jugada.getCartas()) {
            puntuacion = (puntuacion * 10) + calcularPuntuacion(carta);
        }
        return puntuacion;
    }

    public int calcularPuntuacion(Naipe carta) {
        int numero = carta.getNumero();
        switch (numero) {
            case 3:
            case 10:
                return 9;
            case 1:
            case 2:
                return 1;
            default:
                return numero - 1;
        }
    }    
}
