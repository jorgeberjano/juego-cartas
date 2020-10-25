package es.jbp.juegos.mus;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jorge
 */
public class Mesa {

    private EstadoPartida estadoPartida = new EstadoPartida();
    private List<Jugador> jugadores = new ArrayList<>();
    private Listener listener;

    public interface Listener {
        void mensaje(String mensaje);
        void turno(Jugador jugador);
    }
    
    public void agregarJugador(Jugador jugador) {
        jugadores.add(jugador);
        estadoPartida.agregarJugador(jugador);
    }
    
    public void inicializar()
    {
        Mazo mazo = new Mazo();
        mazo.barajar(1000);
        estadoPartida.setMazo(mazo);
    }
    
    public void repartir() throws ExcepcionJuego {
        estadoPartida.recojerCartas();
        // Se reparten 4 cartas a cada jugador
        for (int i = 0; i < 4; i++) {
            for (Jugador jugador : jugadores) {
                estadoPartida.asignarCarta(jugador, estadoPartida.sacarCarta());
            }
        }
    }
    

    public Mazo getMazo() {
        return estadoPartida.getMazo();
    }
    private boolean esTurnoDe(Jugador jugador) {
        int numeroJugador = jugadores.indexOf(jugador);
        return numeroJugador == estadoPartida.getTurnoJugador();
    }
    
    
    public Jugada getJugadaDe(Jugador jugador) {
        return estadoPartida.getJugadaDe(jugador);
    }
    
    public void accionJugador(Jugador jugador, Accion accion) {
        if (!esTurnoDe(jugador)) {
            listener.mensaje("No es el turno del jugador " + jugador);
            return;
        }
        
    }
}
 