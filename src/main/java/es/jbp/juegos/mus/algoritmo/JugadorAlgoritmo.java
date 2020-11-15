package es.jbp.juegos.mus.algoritmo;

import es.jbp.juegos.mus.enumerados.Accion;
import es.jbp.juegos.mus.enumerados.AnimoJugador;
import es.jbp.juegos.mus.enumerados.FaseJuego;
import static es.jbp.juegos.mus.enumerados.FaseJuego.DECIDIR_MUS;
import es.jbp.juegos.mus.enumerados.Lance;
import es.jbp.juegos.mus.iu.PanelJugador;
import es.jbp.juegos.mus.mensajes.MensajeTurno;
import es.jbp.juegos.mus.persistencia.Jugada;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author jorge
 */
public class JugadorAlgoritmo {

    private PanelJugador panelJugador;

    private Jugada jugada;
    private FaseJuego faseJuego;
    private Lance lance;
    private int puntuacionEllos;
    private int puntuacionNosotros;
    private int orden;
    private boolean heCortado;
    private int apuestaAcumulada;
    private int numeroDescartes;

    public JugadorAlgoritmo(PanelJugador panelJugador) {
        this.panelJugador = panelJugador;
    }

    public void mensajeRecibido(Object objeto) {

        if (objeto instanceof MensajeTurno) {            
            MensajeTurno mensaje = (MensajeTurno) objeto;
            System.out.println(panelJugador.getNombreJugador() + " recibe el turno de " + mensaje.getFaseJuego());
            faseJuego = mensaje.getFaseJuego();
            lance = mensaje.getLance();
            orden = mensaje.getOrden();
            heCortado = mensaje.isHeCortado();
            apuestaAcumulada = mensaje.getApuestaAcumulada();
            if (mensaje.getCartas() != null) {
                jugada = new Jugada();
                jugada.setCartas(mensaje.getCartas());
            }
            puntuacionNosotros = mensaje.getPuntuacionNosotros();
            puntuacionEllos = mensaje.getPuntuacionEllos();
            numeroDescartes = mensaje.getNumeroDescartes();

            boolean esMiTurno = Objects.equals(mensaje.getNombreJugadorTurno(), panelJugador.getNombreJugador());
            if (esMiTurno) {
                decidirAccion();
            }
        }
    }

    private AnimoJugador calcularAnimoJugador() {
        int diferencia = Math.abs(puntuacionNosotros - puntuacionEllos);

        if (diferencia < 10) {
            return AnimoJugador.NORMAL;
        } else if (puntuacionNosotros < puntuacionEllos) {
            return diferencia > 10 && puntuacionEllos > 30 ? AnimoJugador.DESESPERADO : AnimoJugador.PERDIENDO;
        } else {
            return AnimoJugador.GANANDO;
        }
    }

    public void decidirAccion() {
        Decisor decisor = null;
        Accion accionPorDefecto = null;
        if (faseJuego == null) {
            return;
        }
        switch (faseJuego) {
            case DECIDIR_MUS:
                decisor = new DecisorCortar();
                accionPorDefecto = Accion.DAR_MUS;
                break;
            case DESCARTE:
                decisor = new DecisorDescarte();
                accionPorDefecto = Accion.DESCARTAR;
                break;
            case APUESTA:
                decisor = new DecisorApuesta();
                accionPorDefecto = Accion.PASAR;
                break;
            case RESPUESTA:
            case RESPUESTA_OTRO:
                decisor = new DecisorRespuesta();
                accionPorDefecto = Accion.NO_QUERER;
                break;
            case RESPUESTA_ORDAGO:
            case RESPUESTA_ORDAGO_OTRO:
                decisor = new DecisorRespuestaOrdago();
                accionPorDefecto = Accion.NO_QUERER;
                break;
        }

        if (decisor == null) {
            return;
        }
       
        Situacion situacion = new Situacion();
        situacion.setAnimoJugador(calcularAnimoJugador());
        situacion.setJugada(jugada);
        situacion.setOrden(orden);
        situacion.setLance(lance);
        situacion.setApuestaActual(apuestaAcumulada);
        situacion.setHeCortado(heCortado);
        situacion.setNumeroDescartes(numeroDescartes);
        
        decisor.setSituacion(situacion);
        Accion accion = decisor.getDecision();
        int apuesta = decisor.getApuesta();
        List<Integer> descartes = decisor.getDescartes();
        if (accion != null) {
            panelJugador.realizarAccion(accion, apuesta, descartes);
            return;
        }       

        if (accionPorDefecto != null) {
            panelJugador.realizarAccion(accionPorDefecto);
        }

    }
}
