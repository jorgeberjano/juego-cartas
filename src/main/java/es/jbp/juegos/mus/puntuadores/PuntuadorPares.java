package es.jbp.juegos.mus.puntuadores;

import es.jbp.juegos.mus.Constantes;
import es.jbp.juegos.mus.Jugada;

/**
 *
 * @author jorge
 */
public class PuntuadorPares extends PuntuadorGrandes {

    @Override
    public int calcularPuntuacion(Jugada jugada) {
        int puntuacion = super.calcularPuntuacion(jugada);
        int pares = calcularPares(jugada);
        puntuacion = pares * 10000 + puntuacion;
        return puntuacion;
    }
    
    
     /**
     * Devuelve el numero que representa los pares en una jugada 2 - un par, 3 -
     * media, 4 - duples
     */
    private int calcularPares(Jugada jugada) {
        int valorAnterior = 0;
        int pares = 0;
        boolean parAnterior = false;
        for (int i = 0; i < Constantes.NUMERO_CARTAS_POR_JUGADOR; i++) {
            int valor = super.calcularPuntuacion(jugada.getCarta(i));
            if (valor == valorAnterior) {
                pares += parAnterior ? 1 : 2;
                parAnterior = true;
            } else {
                parAnterior = false;
            }
            valorAnterior = valor;
        }
        return pares;
    }
    
}
