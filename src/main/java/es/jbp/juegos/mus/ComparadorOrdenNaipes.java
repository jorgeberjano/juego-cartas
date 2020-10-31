package es.jbp.juegos.mus;

import java.util.Comparator;

/**
 *
 * @author jorge
 */
public class ComparadorOrdenNaipes implements Comparator<Naipe> {

    public ComparadorOrdenNaipes() {
    }

    @Override
    public int compare(Naipe naipe1, Naipe naipe2) {
        
        int valor1 = convertirValor(naipe1.getNumero());
        int valor2 = convertirValor(naipe2.getNumero());
        return valor2 - valor1;
    }

    private int convertirValor(int valor) {
         if (valor == 3) {
            valor = 10;
        } else if (valor == 10) {
            valor = 11;
        }
        return valor;
    }
    
    
}
