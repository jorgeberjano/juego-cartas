package es.jbp.juegos.mus;

import java.util.List;

/**
 *
 * @author jorge
 */
public class EstadoJugador {
    private String nombre;
    private int turno;
    private Jugada jugada = new Jugada();

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getTurno() {
        return turno;
    }

    public void setTurno(int turno) {
        this.turno = turno;
    }

    public Jugada getJugada() {
        return jugada;
    }

    public void setJugada(Jugada jugada) {
        this.jugada = jugada;
    }

    public void asignarCarta(Naipe carta) {
        this.jugada.agregar(carta);
    }

    public void recojerCartas() {
        jugada.limpiar();
    }
    
    
    
}
