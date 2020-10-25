package es.jbp.juegos.mus;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jorge
 */
public class Jugada {

    private List<Naipe> cartas = new ArrayList<>();

    public void descartar(int i) {
        if (i >= 0 && i < cartas.size()) {
            cartas.remove(i);
        }
    }

    public void agregar(Naipe naipe) {
        cartas.add(naipe);
    }

    public void ordenar() {
        // TODO
    }

    public List<Naipe> getCartas() {
        return cartas;
    }

    public void limpiar() {
        cartas.clear();
    }

}
