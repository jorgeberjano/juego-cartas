package es.jbp.juegos.mus.mensajes;

import es.jbp.juegos.mus.persistencia.Jugada;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author jorge
 */
public class MensajeCartas extends MensajeJugador {

    private Map<String, Jugada> mapaCartas = new HashMap<>();

    public Map<String, Jugada> getMapaCartas() {
        return mapaCartas;
    }

    public void setMapaCartas(Map<String, Jugada> mapaCartas) {
        this.mapaCartas = mapaCartas;
    }
    
    public void agregarCartasJugador(String nombreJugador, Jugada jugada) {
        mapaCartas.put(nombreJugador, jugada);
    }
        
}
