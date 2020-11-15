package es.jbp.juegos.mus.algoritmo;

import es.jbp.juegos.mus.enumerados.AnimoJugador;
import es.jbp.juegos.mus.enumerados.Lance;
import es.jbp.juegos.mus.persistencia.Jugada;

/**
 *
 * @author jorge
 */
public class Situacion {
    private AnimoJugador animoJugador;
    private int orden;
    private Jugada jugada;
    private Lance lance;            
    private int apuestaActual;
    private boolean heCortado;
    private int numeroDescartes;

    public AnimoJugador getAnimoJugador() {
        return animoJugador;
    }

    public void setAnimoJugador(AnimoJugador animoJugador) {
        this.animoJugador = animoJugador;
    }

    public int getOrden() {
        return orden;
    }

    public void setOrden(int orden) {
        this.orden = orden;
    }

    public Jugada getJugada() {
        return jugada;
    }

    public void setJugada(Jugada jugada) {
        this.jugada = jugada;
    }

    public Lance getLance() {
        return lance;
    }

    public void setLance(Lance lance) {
        this.lance = lance;
    }

    public int getApuestaActual() {
        return apuestaActual;
    }

    public void setApuestaActual(int apuestaActual) {
        this.apuestaActual = apuestaActual;
    }

    public boolean isHeCortado() {
        return heCortado;
    }

    public void setHeCortado(boolean heCortado) {
        this.heCortado = heCortado;
    }

    public int getNumeroDescartes() {
        return numeroDescartes;
    }

    public void setNumeroDescartes(int numeroDescartes) {
        this.numeroDescartes = numeroDescartes;
    }
}
