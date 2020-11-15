package es.jbp.juegos.mus.persistencia;

import es.jbp.juegos.mus.logica.ExcepcionJuego;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author jorge
 */
public class EstadoJugador implements Serializable {
    private String nombreJugador;
    private int indice;
    private Jugada jugada = new Jugada();
    private boolean descartado;

    public EstadoJugador() {
    }

    public EstadoJugador(String nombreJugador, int indice) {
        this.nombreJugador = nombreJugador;
        this.indice = indice;
    }
    
    public int getIndice() {
        return indice;
    }

    public void setIndice(int indice) {
        this.indice = indice;
    }

    public String getNombreJugador() {
        return nombreJugador;
    }

    public void setNombreJugador(String nombreJugador) {
        this.nombreJugador = nombreJugador;
    }

    public Jugada getJugada() {
        return jugada;
    }

    public void setJugada(Jugada jugada) {
        this.jugada = jugada;
    }
    
    public boolean isDescartado() {
        return descartado;
    }

    public void setDescartado(boolean descartado) {
        this.descartado = descartado;
    }

    public void asignarCarta(Naipe carta) {
        this.jugada.agregar(carta);
    }

    public void recojerCartas() {
        jugada.limpiar();
    }
    
    public void ordenarCartas() {
        jugada.ordenar();
    }


    public List<Naipe> obtenerListaCartas() {
        return jugada.getCartas();
    }

    public void descartar(List<Naipe> descartes) throws ExcepcionJuego {
        jugada.descartar(descartes);
    }

    public void comprobarJugadaValida() throws ExcepcionJuego {
        jugada.comprobarJugadaValida();
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 37 * hash + Objects.hashCode(this.nombreJugador);
        hash = 37 * hash + this.indice;
        hash = 37 * hash + Objects.hashCode(this.jugada);
        hash = 37 * hash + (this.descartado ? 1 : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final EstadoJugador other = (EstadoJugador) obj;
        if (this.indice != other.indice) {
            return false;
        }
        if (this.descartado != other.descartado) {
            return false;
        }
        if (!Objects.equals(this.nombreJugador, other.nombreJugador)) {
            return false;
        }
        if (!Objects.equals(this.jugada, other.jugada)) {
            return false;
        }
        return true;
    }

    public boolean tienePares() {
        return jugada.tienePares();
    }
    
    public boolean tieneJuego() {
        return jugada.tieneJuego();
    }

}
