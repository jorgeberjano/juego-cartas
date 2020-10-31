package es.jbp.juegos.mus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author jorge
 */
public class Jugada {

    private List<Naipe> cartas = new ArrayList<>();

    public void descartar(List<Naipe> descartes) throws ExcepcionJuego {

        for (Naipe descarte : descartes) {
            if (!cartas.contains(descarte)) {
                throw new ExcepcionJuego("La jugada no tiene la carta " + descarte.toString());
            }
            cartas.remove(descarte);            
        }
    }

    public void agregar(Naipe naipe) {
        cartas.add(naipe);
    }

    public void ordenar() {
        Collections.sort(cartas, new ComparadorOrdenNaipes());
    }

    public List<Naipe> getCartas() {
        return cartas;
    }

    public void limpiar() {
        cartas.clear();
    }

    public Naipe getCarta(int i) {
        if (i < 0 || i >= cartas.size()) {
            return null;
        }
        return cartas.get(i);
    }

    public int getNumeroCarta(int i) {
        Naipe carta = getCarta(i);
        return carta == null ? 0 : carta.getNumero();
    }
    
    /**
     * Devielve el numero que representa los pares en una jugada
     * 0 - no hay pares, 2 - un par, 3 - medias, 4 - duples
     */
    public int calcularValorPares(Jugada jugada) {
        int valorAnterior = 0;
        int n = 0;
        boolean parAnterior = false;
        for (int i = 0; i < Constantes.NUMERO_CARTAS_POR_JUGADOR; i++) {
            int valor = convertirValorPares(jugada.getNumeroCarta(i));
            if (valor == valorAnterior) {
                n += parAnterior ? 1 : 2;
                parAnterior = true;
            } else {
                parAnterior = false;
            }
            valorAnterior = valor;
        }
        return n == 0 ? 0 : n - 1;
    }

    private int convertirValorPares(int valor) {
        if (valor == 3) {
            valor = 10;
        } else if (valor == 2) {
            valor = 1;
        }
        return valor;
    }

    public void comprobarJugadaValida() throws ExcepcionJuego {
        if (cartas.size() != Constantes.NUMERO_CARTAS_POR_JUGADOR) {
            throw new ExcepcionJuego("La jugada no tiene " +  Constantes.NUMERO_CARTAS_POR_JUGADOR + " cartas");
        }

    }

}
