package es.jbp.juegos.mus.servicios;

import es.jbp.juegos.mus.mensajes.MensajeJugador;
import java.util.HashMap;
import java.util.Map;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.stereotype.Service;

/**
 *
 * @author jorge
 */
@Service
public class ServicioJugadores implements MessageListener {
    private final Map<String, ListenerWebsocket> listenersJugadores = new HashMap<>();
    
    private static final GenericJackson2JsonRedisSerializer serializer = new GenericJackson2JsonRedisSerializer();
        
     /**
     * Procesa un mensaje publicado en Redis
     */
    @Override
    public void onMessage(Message mensaje, byte[] pattern) {
        Object obj = serializer.deserialize(mensaje.getBody());
        if (obj instanceof MensajeJugador) {
            String nombreJugador = ((MensajeJugador) obj).getNombreJugadorDestinatario();
            ListenerWebsocket listener = listenersJugadores.get(nombreJugador);
            if (listener != null) {
                listener.mensajeRecibido(obj);
            }
        }        
    }
       
    public void agregarListenerJugador(String nombreJugador, ListenerWebsocket listener) {
//        if (!nombreJugador.equals("Bernardo")) {
//                return;
//            }
        System.out.println("Se agrega la conexion de " + nombreJugador);
        
        listenersJugadores.put(nombreJugador, listener);
    }
    
    public void eliminarListenerJugador(String nombreJugador) {
        listenersJugadores.remove(nombreJugador);
    }
}
