package es.jbp.juegos.mus.recursos;

import es.jbp.juegos.mus.logica.ExcepcionJuego;
import es.jbp.juegos.mus.mensajes.MensajeDeJugador;
import es.jbp.juegos.mus.servicios.ServicioJugadores;
import es.jbp.juegos.mus.servicios.ServicioMesa;
import es.shs.comun.utiles.depuracion.GestorLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import es.jbp.juegos.mus.servicios.ListenerWebsocket;

/**
 * Este será el controlador rest y servidor de sockets
 *
 * @author jorge
 */
@Service
public class RecursoMesa implements ListenerWebsocket {

    private String idMesa = "prueba";
    
    @Autowired
    private ServicioMesa servicioMesa;

    @Autowired
    private ServicioJugadores servicioJugadores;

    public void inicializar() {
        servicioMesa.desalojarMesa(idMesa);
    }

    public void agregarListenerJugador(String nombreJugador, ListenerWebsocket listenerJugador) {
        servicioJugadores.agregarListenerJugador(nombreJugador, listenerJugador);
    }

    public boolean solicitarEntrada(String nombreJugador, int indice) {
        try {
            return servicioMesa.solicitarEntrada(idMesa, nombreJugador, indice);
        } catch (ExcepcionJuego ex) {
            GestorLog.error("Error al solicitar entrada en la mesa el jugador " + nombreJugador, ex);
            return false;
        }
    }

    @Override
    public void mensajeRecibido(Object mensaje) {
        try {
            if (mensaje instanceof MensajeDeJugador) {
                MensajeDeJugador mensajeDeJugador = (MensajeDeJugador) mensaje;
                servicioMesa.procesarMensajeJugador(idMesa, mensajeDeJugador);
            }
        } catch (ExcepcionJuego ex) {
            GestorLog.error("Error realizar acción del jugador", ex);
        }
    }
}
