
package es.jbp.juegos.mus;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author jorge
 */
public class EstadoPartida {
    private Mazo mazo;
    private Lance lance;
    private EstadoLance estadoLance;
    private int turnoJugador;
    private int puntuacionEquipo1;
    private int puntuacionEquipo2;
    private final Map<String, EstadoJugador> estadosJugadores = new HashMap<>();
    

    public Mazo getMazo() {
        return mazo;
    }

    public void setMazo(Mazo mazo) {
        this.mazo = mazo;
    }

    public Lance getLance() {
        return lance;
    }

    public void setLance(Lance lance) {
        this.lance = lance;
    }

    public EstadoLance getEstadoLance() {
        return estadoLance;
    }

    public void setEstadoLance(EstadoLance estadoLance) {
        this.estadoLance = estadoLance;
    }

    public int getPuntuacionEquipo1() {
        return puntuacionEquipo1;
    }

    public int getPuntuacionEquipo2() {
        return puntuacionEquipo2;
    }


    public int getTurnoJugador() {
        return turnoJugador;
    }

    public Jugada getJugadaDe(Jugador jugador) {
        EstadoJugador estadoJugador = estadosJugadores.get(jugador.getNombre());
        if (estadoJugador == null) {
            System.err.println("El jugador no tiene estado");
            return null;
        }
        return estadoJugador.getJugada();
    }

    public void asignarCarta(Jugador jugador, Naipe carta) throws ExcepcionJuego {
       getEstadoJugador(jugador).asignarCarta(carta);
    }

    public Naipe sacarCarta() {
        return mazo.sacar();
    }

    private EstadoJugador getEstadoJugador(Jugador jugador) throws ExcepcionJuego {
        if (jugador == null) {
            throw new ExcepcionJuego("No ha indicadp el jugador");
        }
        
        EstadoJugador estado = estadosJugadores.get(jugador.getNombre());
        if (estado == null) {
            throw new ExcepcionJuego("El jugador no tiene estado");
        }
        return estado;
    }

    public void agregarJugador(Jugador jugador) {
        estadosJugadores.put(jugador.getNombre(), new EstadoJugador());
    }

    public void recojerCartas() {
        for (EstadoJugador estadoJugador : estadosJugadores.values()) {
            estadoJugador.recojerCartas();
        }
    }

}
