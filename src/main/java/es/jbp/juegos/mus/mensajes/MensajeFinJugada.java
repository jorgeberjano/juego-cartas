package es.jbp.juegos.mus.mensajes;

import es.jbp.juegos.mus.persistencia.Naipe;
import java.util.List;

/**
 *
 * @author jorge
 */
public class MensajeFinJugada {
    private List<Naipe> cartasJugador0;
    private List<Naipe> cartasJugador1;
    private List<Naipe> cartasJugador2;
    private List<Naipe> cartasJugador3;
    private int puntuacionNosotros;
    private int puntuacionEllos;

    public List<Naipe> getCartasJugador0() {
        return cartasJugador0;
    }

    public void setCartasJugador0(List<Naipe> cartasJugador0) {
        this.cartasJugador0 = cartasJugador0;
    }

    public List<Naipe> getCartasJugador1() {
        return cartasJugador1;
    }

    public void setCartasJugador1(List<Naipe> cartasJugador1) {
        this.cartasJugador1 = cartasJugador1;
    }

    public List<Naipe> getCartasJugador2() {
        return cartasJugador2;
    }

    public void setCartasJugador2(List<Naipe> cartasJugador2) {
        this.cartasJugador2 = cartasJugador2;
    }

    public List<Naipe> getCartasJugador3() {
        return cartasJugador3;
    }

    public void setCartasJugador3(List<Naipe> cartasJugador3) {
        this.cartasJugador3 = cartasJugador3;
    }

    public int getPuntuacionNosotros() {
        return puntuacionNosotros;
    }

    public void setPuntuacionNosotros(int puntuacionNosotros) {
        this.puntuacionNosotros = puntuacionNosotros;
    }

    public int getPuntuacionEllos() {
        return puntuacionEllos;
    }

    public void setPuntuacionEllos(int puntuacionEllos) {
        this.puntuacionEllos = puntuacionEllos;
    }
    
    
}
