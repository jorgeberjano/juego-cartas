package es.jbp.juegos.mus.mensajes;

/**
 *
 * @author jorge
 */
public class MensajeTexto extends MensajeJugador {

    private String nombreJugadorRemitente;
    private String texto;
    
    public String getNombreJugadorRemitente() {
        return nombreJugadorRemitente;
    }

    public void setNombreJugadorRemitente(String nombreJugadorRemitente) {
        this.nombreJugadorRemitente = nombreJugadorRemitente;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }
}
