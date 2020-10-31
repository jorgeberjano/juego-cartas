package es.jbp.juegos.mus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Stack;

/**
 *
 * @author jorge
 */
public class Baraja {
    private final Stack<Naipe> mazo = new Stack<>();
    
    private final List<Naipe> descartes = new ArrayList();

    public Baraja() {
    }
    
    public void inicializar() {
        mazo.clear();
        for (int i = 1; i <= 10; i++) {
            mazo.add(new Naipe(i, Palo.OROS));
            mazo.add(new Naipe(i, Palo.COPAS));
            mazo.add(new Naipe(i, Palo.BASTOS));
            mazo.add(new Naipe(i, Palo.ESPADAS));
        }
    }
    
    public void barajarMazo(int n) {
        int numeroCartas = mazo.size();
        if (numeroCartas <= 0) {
            return;
        }
        Random rand = new Random();
        for (int i = 1; i < n; i++) {
            int a = rand.nextInt(numeroCartas);
            int b = rand.nextInt(numeroCartas);
            Collections.swap(mazo, a, b);
        }
    }

    /**
     * Extrae una carta del mazo. Cuando se acaban las cartas del mazo 
     * los descartes pasan a a ser el nuevo mazo y se vuelve a barajar.
     * @return La carta extraida
     */
    public Naipe extraerDelMazo() throws ExcepcionJuego {        
        if (mazo.isEmpty()) {
            mazo.addAll(descartes);
            barajarMazo(100);
        }
        if (mazo.isEmpty()) {
            throw new ExcepcionJuego("No hay mas cartas en el mazo");
        }
        return mazo.pop();
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (Naipe naipe : mazo) {
            builder.append(naipe.toString());
            builder.append(" ");
        }
        return builder.toString();
    }

    public List<Naipe> getNaipes() {
      return new ArrayList(mazo);
    }

    public void agregarDescartes(List<Naipe> cartas) {           
        descartes.addAll(cartas);    
    }
}
