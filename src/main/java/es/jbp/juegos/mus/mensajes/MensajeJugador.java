package es.jbp.juegos.mus.mensajes;

import java.io.Serializable;

/**
 *
 * @author jorge
 */
public class MensajeJugador implements Serializable {
    private String nombreJugadorDestinatario;
    private int msRetraso = 0;

    public String getNombreJugadorDestinatario() {
        return nombreJugadorDestinatario;
    }

    public void setNombreJugadorDestinatario(String nombreJugadorDestinatario) {
        this.nombreJugadorDestinatario = nombreJugadorDestinatario;
    }

    public int getMsRetraso() {
        return msRetraso;
    }

    public void setMsRetraso(int msRetraso) {
        this.msRetraso = msRetraso;
    }
    
}
