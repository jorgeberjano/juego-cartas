package es.jbp.juegos.mus.persistencia;

import es.jbp.juegos.mus.enumerados.Palo;
import java.io.Serializable;

/**
 *
 * @author jorge
 */
public class Naipe implements Serializable {

    private int numero;
    private Palo palo;

    public Naipe() {
    }
    
    public Naipe(int numero, Palo palo) {
        this.numero = numero;
        this.palo = palo;
    }

    @Override
    public String toString() {
        return obtenerTextoNumero() + " " + palo;
    }

    public int getNumero() {
        return numero;
    }

    public String obtenerTextoNumero() {
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
    
    public int obtenerOrden() {
        switch (numero) {
            case 3:
                return 10;
            case 10:
                return 11;
            default:
                return numero;
        }
    }

}
