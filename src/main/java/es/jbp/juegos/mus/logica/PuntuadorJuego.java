package es.jbp.juegos.mus.logica;

import es.jbp.juegos.mus.persistencia.Jugada;
import es.jbp.juegos.mus.persistencia.Naipe;

/**
 *
 * @author jorge
 */
public class PuntuadorJuego extends PuntuadorGrandes {

    @Override
    public int calcularPuntuacion(Jugada jugada) {
        int puntuacion = 0;
        for (Naipe carta : jugada.getCartas()) {
            puntuacion += calcularValorJuego(carta);
        }
        
        switch (puntuacion) {
            case 31:
                return 42;
            case 32:
                return 41;
            default:
                return puntuacion;
        }
    }
    
    public int calcularValorJuego(Naipe carta) {  
        int numeroCarta = carta.getNumero();
        
        switch (numeroCarta) {
            case 3:
            case 8:
            case 9:
                return 10;
            case 2:
                return 1;
            default:
                return numeroCarta;
        }        
    }
}
