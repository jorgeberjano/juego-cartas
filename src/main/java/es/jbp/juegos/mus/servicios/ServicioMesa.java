package es.jbp.juegos.mus.servicios;

import es.jbp.juegos.mus.logica.LogicaMesa;
import es.jbp.juegos.mus.logica.Constantes;
import es.jbp.juegos.mus.logica.ExcepcionJuego;
import es.jbp.juegos.mus.persistencia.EstadoPartida;
import es.jbp.juegos.mus.mensajes.MensajeDeJugador;
import es.jbp.juegos.mus.mensajes.MensajeJugador;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 *
 * @author jorge
 */
@Service
public class ServicioMesa {
    
    @Autowired
    private RedisTemplate<String, String> stringTemplate;
    
    @Autowired
    private RedisTemplate<String, EstadoPartida> estadoPartidaTemplate;
    
    @Autowired
    private RedisTemplate<String, Object> mensajeTemplate;   
           
    private String keySolicitud(String idMesa, int indice) {
        return "mesa:" + idMesa + ".solicitud:" + indice;
    }  
    
    private String keyEstadoPartida(String idMesa) {
        return "mesa:" + idMesa + ".estado";
    }    
        
    public boolean estaDentro(String idMesa, String nombreJugador) {
        ValueOperations<String, String> ops = stringTemplate.opsForValue();
        for (int i = 0; i < Constantes.NUMERO_JUGADORES; i++) {
            if (nombreJugador.equals(ops.get(keySolicitud(idMesa, i)))) {
                return true;
            }
        }
        return false;
    }
    
    public boolean solicitarEntrada(String idMesa, String nombreJugador, int indice) throws ExcepcionJuego {
        ValueOperations<String, String> ops = stringTemplate.opsForValue();
        String nombreJugadorActual = ops.get(keySolicitud(idMesa, indice));
        if (nombreJugadorActual != null) {
            return false;
        }
        ops.set(keySolicitud(idMesa, indice), nombreJugador);        
        comprobarInicioPartida(idMesa);     
        return true;
    }
    
    private void borrarSolicitudes(String idMesa) {
        for (int i = 0; i < Constantes.NUMERO_JUGADORES; i++) {
            stringTemplate.delete(keySolicitud(idMesa, i));        
        }
    }    
    
    public void desalojarMesa(String idMesa) {
        borrarSolicitudes(idMesa);
        estadoPartidaTemplate.delete(keyEstadoPartida(idMesa));
    }
    private boolean comprobarInicioPartida(String idMesa) throws ExcepcionJuego {
        List<String> listaNombresJugadores = new ArrayList<>();
        ValueOperations<String, String> ops = stringTemplate.opsForValue();        
        for (int i = 0; i < Constantes.NUMERO_JUGADORES; i++) {
            String nombreJugador = ops.get(keySolicitud(idMesa, i));
            if (nombreJugador == null) {                
                return false;
            }
            listaNombresJugadores.add(nombreJugador);
        }
        
        LogicaMesa mesa = new LogicaMesa();
        mesa.setIdMesa(idMesa);
        mesa.setEstadoPartida(new EstadoPartida());
        mesa.inicioPartida(listaNombresJugadores);
        
        persistirEstado(idMesa, mesa.getEstadoPartida());
        
        enviarMensajesAJugadores(mesa);
        
        return true;
    }
    
    public void procesarMensajeJugador(String idMesa, MensajeDeJugador mensaje) throws ExcepcionJuego {      
        EstadoPartida estadoPartida = recuperarEstado(idMesa);
        LogicaMesa mesa = new LogicaMesa();
        mesa.setIdMesa(idMesa);
        mesa.setEstadoPartida(estadoPartida);
        mesa.procesarMensajeJugador(mensaje);
        
        persistirEstado(idMesa, estadoPartida);        
        enviarMensajesAJugadores(mesa);
        
//        mesa.iniciarSiguienteMano();
//        persistirEstado(mesa.getIdMesa(), mesa.getEstadoPartida());
//        enviarMensajesAJugadores(mesa);
        
//        if (mesa.isPausaFinMano()) {
//            listaMesasInicioMano.add(mesa);
//        }
    }
    
    private EstadoPartida recuperarEstado(String idMesa) {
        ValueOperations<String, EstadoPartida> ops = estadoPartidaTemplate.opsForValue();
        EstadoPartida estadoPartida = ops.get(keyEstadoPartida(idMesa));
        if (estadoPartida == null) {
            estadoPartida = new EstadoPartida();
        }
        return estadoPartida;
    }
    
    private void persistirEstado(String idMesa, EstadoPartida estadoPartida) {
        ValueOperations<String, EstadoPartida> ops = estadoPartidaTemplate.opsForValue();
        ops.set(keyEstadoPartida(idMesa), estadoPartida);
    }    
    
    
    private void enviarMensaje(MensajeJugador mensaje) {
        mensajeTemplate.convertAndSend("mensajes", mensaje);
    }
            
    private void enviarMensajesAJugadores(LogicaMesa mesa) throws ExcepcionJuego {       
        List<MensajeJugador> mensajes = mesa.obtenerMensajesAJugadores();
        
        if (mensajes.isEmpty()) {
            return;
        }
        
        List<Mono<MensajeJugador>> lista = mensajes.stream().map(m -> Mono.just(m).delayElement(Duration.ofMillis(m.getMsRetraso()))).collect(Collectors.toList());
        Flux<MensajeJugador> flux = Flux.empty();
        for (Mono<MensajeJugador> mono: lista) {
            flux = Flux.concat(flux, mono);
        }           
        flux.subscribe(m -> enviarMensaje(m));
    }

    
            
}
