package es.jbp.juegos.mus.mensajes;

import es.jbp.juegos.mus.enumerados.Accion;

/**
 *
 * @author jorge
 */
public class MensajeDeJugador extends MensajeJugador {
    private Accion accion;
    private String texto;
    private int apuesta;
    private int[] descartes;

    public Accion getAccion() {
        return accion;
    }

    public void setAccion(Accion accion) {
        this.accion = accion;
    }

    public String getMensaje() {
        return texto;
    }

    public void setMensaje(String texto) {
        this.texto = texto;
    }

    public int getApuesta() {
        return apuesta;
    }

    public void setApuesta(int apuesta) {
        this.apuesta = apuesta;
    }

    public int[] getDescartes() {
        return descartes;
    }

    public void setDescartes(int[] descartes) {
        this.descartes = descartes;
    }
}
