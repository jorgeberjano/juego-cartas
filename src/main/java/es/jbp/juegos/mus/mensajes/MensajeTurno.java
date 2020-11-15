package es.jbp.juegos.mus.mensajes;

import es.jbp.juegos.mus.enumerados.FaseJuego;
import es.jbp.juegos.mus.enumerados.Lance;
import es.jbp.juegos.mus.persistencia.Naipe;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jorge
 */
public class MensajeTurno extends MensajeJugador {

    private String nombreJugadorTurno;
    private Lance lance;
    private FaseJuego faseJuego;
    private List<Naipe> cartas;
    private int orden;
    private boolean heCortado;
    private int puntuacionNosotros;
    private int puntuacionEllos;
    private int apuestaAcumulada;
    private int numeroDescartes;

    public String getNombreJugadorTurno() {
        return nombreJugadorTurno;
    }

    public void setNombreJugadorTurno(String nombreJugadorTurno) {
        this.nombreJugadorTurno = nombreJugadorTurno;
    }

    public List<Naipe> getCartas() {
        return cartas;
    }

    public void setCartas(List<Naipe> cartas) {
        this.cartas = cartas;
    }

    public Lance getLance() {
        return lance;
    }

    public void setLance(Lance lance) {
        this.lance = lance;
    }

    public FaseJuego getFaseJuego() {
        return faseJuego;
    }

    public void setFaseJuego(FaseJuego faseJuego) {
        this.faseJuego = faseJuego;
    }

    public int getOrden() {
        return orden;
    }

    public void setOrden(int orden) {
        this.orden = orden;
    }

    public boolean isHeCortado() {
        return heCortado;
    }

    public void setHeCortado(boolean heCortado) {
        this.heCortado = heCortado;
    }

    public int getPuntuacionNosotros() {
        return puntuacionNosotros;
    }

    public void setPuntuacionNosotros(int puntuacionNosotros) {
        this.puntuacionNosotros = puntuacionNosotros;
    }

    public int getPuntuacionEllos() {
        return puntuacionEllos;
    }

    public void setPuntuacionEllos(int puntuacionEllos) {
        this.puntuacionEllos = puntuacionEllos;
    }

    public int getApuestaAcumulada() {
        return apuestaAcumulada;
    }

    public void setApuestaAcumulada(int apuestaAcumulada) {
        this.apuestaAcumulada = apuestaAcumulada;
    }

    public int getNumeroDescartes() {
        return numeroDescartes;
    }

    public void setNumeroDescartes(int numeroDescartes) {
        this.numeroDescartes = numeroDescartes;
    }
}
