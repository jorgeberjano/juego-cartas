package es.jbp.juegos.mus.logica;

import es.jbp.juegos.mus.persistencia.Jugada;

/**
 *
 * @author jorge
 */
public class PuntuadorPares extends PuntuadorGrandes {

    @Override
    public int calcularPuntuacion(Jugada jugada) {

        if (jugada.getCartas().size() != Constantes.NUMERO_CARTAS_POR_JUGADOR) {
            return 0;
        }

        int p0 = calcularPuntuacion(jugada.obtenerCarta(0));
        int p1 = calcularPuntuacion(jugada.obtenerCarta(1));
        int p2 = calcularPuntuacion(jugada.obtenerCarta(2));
        int p3 = calcularPuntuacion(jugada.obtenerCarta(3));

        if (iguales(p0, p1) && iguales(p2, p3)) {
            return p0 * 1000 + p1 * 100 + p2 * 10 + p3;
        } else if (iguales(p0, p1, p2)) {
            return p0 * 100 + p1 * 10 + p2;
        } else if (iguales(p1, p2, p3)) {
            return p1 * 100 + p2 * 10 + p3;
        } else if (iguales(p0, p1)) {
            return p0 * 10 + p1;
        } else if (iguales(p1, p2)) {
            return p1 * 10 + p2;
        } else if (iguales(p2, p3)) {
            return p2 * 10 + p3;
        } else {
            return 0;
        }
    }

    private boolean iguales(int... arg) {
        for (int p : arg) {
            if (p != arg[0]) {
                return false;
            }
        }
        return true;
    }

}
