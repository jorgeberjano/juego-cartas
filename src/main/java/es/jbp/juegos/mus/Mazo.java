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
public class Mazo {
    private Stack<Naipe> naipes = new Stack<>();

    public Mazo() {
        for (int i = 1; i <= 10; i++) {
            naipes.add(new Naipe(i, Palo.OROS));
            naipes.add(new Naipe(i, Palo.COPAS));
            naipes.add(new Naipe(i, Palo.BASTOS));
            naipes.add(new Naipe(i, Palo.ESPADAS));
        }
    }
    
    public void barajar(int n) {
        int numeroCartas = naipes.size();
        Random rand = new Random();
        for (int i = 1; i < n; i++) {
            int a = rand.nextInt(numeroCartas);
            int b = rand.nextInt(numeroCartas);
            Collections.swap(naipes, a, b);
        }
    }
    
    public Naipe sacar() {
        return naipes.pop();
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (Naipe naipe : naipes) {
            builder.append(naipe.toString());
            builder.append(" ");
        }
        return builder.toString();
    }

    public List<Naipe> getNaipes() {
      return new ArrayList(naipes);
    }
}
