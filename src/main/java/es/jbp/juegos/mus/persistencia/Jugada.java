package es.jbp.juegos.mus.persistencia;

import es.jbp.juegos.mus.logica.ComparadorOrdenNaipes;
import es.jbp.juegos.mus.logica.Constantes;
import es.jbp.juegos.mus.logica.ExcepcionJuego;
import es.jbp.juegos.mus.logica.PuntuadorChicas;
import es.jbp.juegos.mus.logica.PuntuadorGrandes;
import es.jbp.juegos.mus.logica.PuntuadorJuego;
import es.jbp.juegos.mus.logica.PuntuadorPares;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author jorge
 */
public class Jugada implements Serializable {
    private final static PuntuadorGrandes puntuadorGrandes = new PuntuadorGrandes();
    private final static PuntuadorChicas puntuadorChicas = new PuntuadorChicas();
    private final static PuntuadorPares puntuadorPares = new PuntuadorPares();
    private final static PuntuadorJuego puntuadorJuego = new PuntuadorJuego();
    
    private List<Naipe> cartas = new ArrayList<>();
    
    private int grandes;
    private int chicas;
    private int pares;
    private int juego;

    public void setCartas(List<Naipe> cartas) {
        this.cartas = cartas;
        grandes = puntuadorGrandes.calcularPuntuacion(this);        
        chicas = puntuadorChicas.calcularPuntuacion(this);
        pares = puntuadorPares.calcularPuntuacion(this);
        juego = puntuadorJuego.calcularPuntuacion(this);        
    }
    
    public List<Naipe> getCartas() {
       return cartas;
    }    

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

    public void limpiar() {
        cartas.clear();
    }

    public Naipe obtenerCarta(int i) {
        if (i < 0 || i >= cartas.size()) {
            return null;
        }
        return cartas.get(i);
    }

    public int obtenerNumeroCarta(int i) {
        Naipe carta = obtenerCarta(i);
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
            int valor = convertirValorPares(jugada.obtenerNumeroCarta(i));
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

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (Naipe carta : cartas) {                
            builder.append(carta.obtenerTextoNumero());
        }
        return builder.toString();
    }
    
    public boolean tieneJuego() {
        return juego >= 31;
    }
    
    public boolean tienePares() {
        return pares > 0;
    }
    
    public boolean tieneDuples() {
        return pares >= 1000;
    }

    public boolean tieneMedia() {
        return pares >= 100 && pares < 1000;
    }
    
    public boolean tiene31() {
        return juego == 42;
    }
    
    public boolean tiene32() {
        return juego == 41;
    }
    
    
    public int valorNumericoGrandes() {
        return grandes;
    }
    
    public int valorNumericoChicas() {
        return chicas;
    }
    
    public int valorNumericoPares() {
        return chicas;
    }
    
    public int valorNumericoJuego() {
        return juego;
    }
}
