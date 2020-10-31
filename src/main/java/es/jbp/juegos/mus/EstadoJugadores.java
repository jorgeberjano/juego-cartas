package es.jbp.juegos.mus;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author jorge
 */
public class EstadoJugadores {
    
    private final List<EstadoJugador> listaEstadosJugadores = new ArrayList<>();

    public EstadoJugador getEstadoJugador(String nombre) throws ExcepcionJuego {
        Optional<EstadoJugador> optEstadoJugador = listaEstadosJugadores.stream().filter(estadoJugador -> nombre.equals(estadoJugador.getNombre())).findFirst();
        if (!optEstadoJugador.isPresent() || optEstadoJugador.get() == null) {
            throw new ExcepcionJuego("El jugador " + nombre + " no tiene estado");
        }                
        return optEstadoJugador.get();
    }
    
    public EstadoJugador getEstadoJugador(int indice) throws ExcepcionJuego {
        Optional<EstadoJugador> optEstadoJugador = listaEstadosJugadores.stream().filter(estadoJugador -> indice == estadoJugador.getIndice()).findFirst();
        if (!optEstadoJugador.isPresent() || optEstadoJugador.get() == null) {
            throw new ExcepcionJuego("El jugador " + indice + " no tiene estado");
        }                
        return optEstadoJugador.get();
    }

    public void agregarJugador(String nombre, int indice) {        
        listaEstadosJugadores.add(new EstadoJugador(nombre, indice));
    }

    public void recojerCartas() {        
        listaEstadosJugadores.stream().forEach(estadoJugador -> estadoJugador.recojerCartas());
    }

    public boolean hanDescartadoTodos() {
        return listaEstadosJugadores.stream().allMatch(estado -> estado.isDescartado());
    }
    
    public void ordenarCartas() {
        listaEstadosJugadores.forEach(estado -> estado.ordenarCartas());
    }

    public List<EstadoJugador> getListaEstadosJugadores() {
        return listaEstadosJugadores;
    }
}
