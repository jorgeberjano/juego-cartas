package es.jbp.juegos.mus;

/**
 *
 * @author jorge
 */
public class Naipe {

    private int numero;
    private Palo palo;

    public Naipe(int numero, Palo palo) {
        this.numero = numero;
        this.palo = palo;
    }

    @Override
    public String toString() {
        return getTextoNumero() + " " + palo;
    }

    public int getNumero() {
        return numero;
    }

    public String getTextoNumero() {
        if (numero < 8) {
            return Integer.toString(numero);
        } else {
            switch (numero) {
                case 8:
                    return "J";
                case 9:
                    return "Q";
                case 10:
                    return "K";
            }
        }
        return "?";
    }

    public Palo getPalo() {
        return palo;
    }

}
