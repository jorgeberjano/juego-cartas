package es.jbp.juegos.mus.logica;

import es.jbp.juegos.mus.enumerados.Accion;
import static es.jbp.juegos.mus.enumerados.Accion.CORTAR;
import static es.jbp.juegos.mus.enumerados.Accion.DAR_MUS;
import static es.jbp.juegos.mus.enumerados.Accion.DESCARTAR;
import static es.jbp.juegos.mus.enumerados.Accion.ENVIDAR;
import static es.jbp.juegos.mus.enumerados.Accion.LANZAR_ORDAGO;
import static es.jbp.juegos.mus.enumerados.Accion.NO_QUERER;
import static es.jbp.juegos.mus.enumerados.Accion.PASAR;
import static es.jbp.juegos.mus.enumerados.Accion.QUERER;
import es.jbp.juegos.mus.enumerados.FaseJuego;
import es.jbp.juegos.mus.enumerados.Lance;
import static es.jbp.juegos.mus.enumerados.Lance.CHICAS;
import static es.jbp.juegos.mus.enumerados.Lance.GRANDES;
import static es.jbp.juegos.mus.enumerados.Lance.JUEGO;
import static es.jbp.juegos.mus.enumerados.Lance.PARES;
import static es.jbp.juegos.mus.enumerados.Lance.PUNTO;
import es.jbp.juegos.mus.enumerados.Tienen;
import es.jbp.juegos.mus.mensajes.MensajeCartas;
import es.jbp.juegos.mus.mensajes.MensajeTurno;
import es.jbp.juegos.mus.mensajes.MensajeDeJugador;
import es.jbp.juegos.mus.mensajes.MensajeJugador;
import es.jbp.juegos.mus.mensajes.MensajeTexto;
import es.jbp.juegos.mus.persistencia.EstadoJugador;
import es.jbp.juegos.mus.persistencia.EstadoPartida;
import es.jbp.juegos.mus.persistencia.Naipe;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author jorge
 */
public class LogicaMesa {
    private String idMesa = "prueba";
    private EstadoPartida estadoPartida;
    private List<MensajeJugador> listaMensajes = new ArrayList<>();
    
    public String getIdMesa() {
        return idMesa;
    }

    public void setIdMesa(String idMesa) {
        this.idMesa = idMesa;
    }

    public EstadoPartida getEstadoPartida() {
        return estadoPartida;
    }

    public void setEstadoPartida(EstadoPartida estadoPartida) {
        this.estadoPartida = estadoPartida;
    }
    
    public void agregarMensaje(String nombreJugador, MensajeJugador mensaje) {
        listaMensajes.add(mensaje);
    }
    
    public void inicioPartida(List<String> listaNombresJugadores) throws ExcepcionJuego {                       
        listaNombresJugadores.stream().forEach(nombreJugador -> estadoPartida.agregarJugador(nombreJugador));
        
        iniciarJuego(estadoPartida.getIndiceJugadorMano());
        
        agregarMensajesTurno();
    }   
        
    public void procesarMensajeJugador(MensajeDeJugador mensaje) throws ExcepcionJuego {        
        
        EstadoJugador jugador = estadoPartida.obtenerEstadoJugador(mensaje.getNombreJugadorDestinatario());
        Accion accion = mensaje.getAccion();
        int[] descartes = mensaje.getDescartes();
        int apuesta = mensaje.getApuesta();
        
        if (!estadoPartida.isEmpezada()) {
            agregarMensajesTexto(jugador.getNombreJugador(), "La partida no ha empezado");
            return;
        } else if (estadoPartida.isTerminada()) {
            agregarMensajesTexto(jugador.getNombreJugador(), "La partida ha terminado");
            return;
        } else if (!esTurnoJugador(jugador.getNombreJugador())) {
            agregarMensajesTexto(jugador.getNombreJugador(), "No es tu turno");
            return;
        } else if (!sePuedeRealizarAccion(accion)) {
            agregarMensajesTexto(jugador.getNombreJugador(), "No se puede " + accion + " en este momento");
            return;
        }        

        switch (accion) {
            case DAR_MUS:
                accionDarMus(jugador);
                break;
            case CORTAR:
                accionCortar(jugador);
                break;
            case DESCARTAR:
                accionDescartar(jugador, descartes);
                break;
            case PASAR:
                accionPasar(jugador);
                break;
            case ENVIDAR:
                accionEnvidar(jugador, apuesta);
                break;
            case QUERER:
                accionQuerer(jugador);
                break;
            case NO_QUERER:
                accionNoQuerer(jugador);
                break;
            case LANZAR_ORDAGO:
                accionLanzarOrdago(jugador);
                break;
        }        
        
        agregarMensajesTurno();
    }
 

    private void repartir() throws ExcepcionJuego {
        estadoPartida.recojerCartasJugadores();
        // Se reparten las cartas a cada jugador
        for (int i = 0; i < Constantes.NUMERO_CARTAS_POR_JUGADOR; i++) {
            for (EstadoJugador jugador : estadoPartida.getListaEstadosJugadores()) {
                jugador.asignarCarta(estadoPartida.sacarCarta());
            }
        }        
        estadoPartida.ordenarCartasJugadores();
    }

    private void repartirTrasDescarte() throws ExcepcionJuego {
        for (EstadoJugador estadoJugador : estadoPartida.getListaEstadosJugadores()) {
            estadoJugador.setDescartado(false);
            int numeroCartas = estadoJugador.obtenerListaCartas().size();
            for (int i = numeroCartas; i < Constantes.NUMERO_CARTAS_POR_JUGADOR; i++) {
                estadoJugador.asignarCarta(estadoPartida.sacarCarta());
            }                     
        }
        
        estadoPartida.ordenarCartasJugadores();       
    }

    private void iniciarJuego(int indiceJugadorMano) throws ExcepcionJuego {
        estadoPartida.setEmpezada(true);
        estadoPartida.setTerminada(false);
        estadoPartida.setIndiceJugadorMano(indiceJugadorMano);
        estadoPartida.setPrimeraMano(true);
        iniciarMano();
    }
       
    private void iniciarMano() throws ExcepcionJuego {
        
        estadoPartida.barajar();
        
        estadoPartida.setLance(null);
        estadoPartida.setFaseJuego(FaseJuego.DECIDIR_MUS);
                
        repartir();
        
        String nombreJugadorMano = estadoPartida.obtenerNombreJugadorMano();
        if (!estadoPartida.isPrimeraMano()) {
            agregarMensajesTexto(nombreJugadorMano, "Soy mano", 500);
        }
        
        asignarTurnoPrimerJugador();
    }
    
    private void iniciarLance(Lance lance) throws ExcepcionJuego {
        estadoPartida.setApuestaAcumulada(0);
        estadoPartida.setIndiceJugadorTurno(estadoPartida.getIndiceJugadorMano());
        estadoPartida.setLance(lance);
        estadoPartida.setFaseJuego(FaseJuego.APUESTA);
        agregarMensajesTexto(null, "Lance de " + lance);
        
        asignarTurnoPrimerJugador();
        for (int i = 0; i < Constantes.NUMERO_JUGADORES; i++) {
            boolean puede = comprobarJugadorTurnoPuedeJugarLance();
            if (puede) {
                break;
            }
            asignarTurnoSiguienteJugador();
        }        
    }
    
    private boolean comprobarJugadorTurnoPuedeJugarLance() throws ExcepcionJuego {
        
        if (estadoPartida.getLance() == null) {
            return true;
        }
        
        EstadoJugador estadoJugador = estadoPartida.obtenerEstadoJugadorTurno();        
        
        boolean puede;
        switch (estadoPartida.getLance()) {
            case PARES:
                puede = estadoJugador.tienePares();
                break;
            case JUEGO:
                puede = estadoJugador.tieneJuego();
                break;
            default:
                puede = true;
        }
        return puede;
    }
    
    
    private void finalizarLance() throws ExcepcionJuego {
        
        Lance lance = estadoPartida.getLance();
        if (lance == null) {
            lance = Lance.GRANDES;                            
        } else switch(lance) {
            case GRANDES:
                lance = Lance.CHICAS;
                break;
            case CHICAS:
                lance = Lance.PARES;
                break;
            case PARES:
                lance = Lance.JUEGO;
                break;
            case JUEGO:
            case PUNTO:
                finalizarMano();
                return;
        }        
        comprobarIniciarLance(lance);
    }
    
    private void comprobarIniciarLance(Lance lance) throws ExcepcionJuego {
        Tienen tienen = sePuedeJugarLance(lance);        
        if (lance == PARES) {
            estadoPartida.setHuboPares(tienen != Tienen.NADIE);
        } else if (lance == JUEGO) {
            estadoPartida.setHuboJuego(tienen != Tienen.NADIE);
        }
        
        if (lance == PARES && tienen != Tienen.AMBOS_EQUIPOS) {
            comprobarIniciarLance(JUEGO);
            return;
        } else if (lance == JUEGO && tienen == Tienen.NADIE) {
            agregarMensajesTexto(null, "Se juega al punto");
            lance = PUNTO;
        } else if (lance == JUEGO && (tienen == Tienen.EQUIPO_0Y2 || tienen == Tienen.EQUIPO_1Y3)) {
            String nombreEquipo = estadoPartida.obtenerNombreEquipo(tienen == Tienen.EQUIPO_0Y2 ? 0 : 1);
            agregarMensajesTexto(null, "Solo " + nombreEquipo + " tienen " + lance.toString().toLowerCase());
            finalizarMano();
            return;
        }
        
        iniciarLance(lance);
    }
    
    private void finalizarMano() throws ExcepcionJuego {        
        agregarMensajesTexto(null, "Termina la mano");
        
        agregarMensajesMostrarCartas();            
        
        // Se hace el recuento de puntos
        for (Lance lance : Lance.values()) {
            hacerRecuento(lance);
            if (estadoPartida.hayEquipoGanador()) {
                String nombreEquipoGanador = estadoPartida.obtenerNombreEquipoGanador();
                agregarMensajesTexto(null, "Los ganadores del juego son " + nombreEquipoGanador);
                finalizarJuego();
                return;
            }
        }         
        iniciarSiguienteMano();
    }
    
    public void iniciarSiguienteMano() throws ExcepcionJuego {
        int indice = normalizarIndiceJugador(estadoPartida.getIndiceJugadorMano() + 1);
        estadoPartida.setIndiceJugadorMano(indice);   
        estadoPartida.setPrimeraMano(false);
        iniciarMano();
    }
    
    private void finalizarJuego() throws ExcepcionJuego {
        estadoPartida.setTerminada(true);
        estadoPartida.setFaseJuego(null); 
        estadoPartida.setLance(null);
        agregarMensajesTexto(null, "El juego ha finalizado");
    }
    
    private void asignarTurnoPrimerJugador() throws ExcepcionJuego {
        int indice = estadoPartida.getIndiceJugadorMano();
        estadoPartida.setIndiceJugadorTurno(indice);        
    }
    
    private Tienen sePuedeJugarLance(Lance lance) throws ExcepcionJuego {
        if (lance != PARES && lance != JUEGO) {
            return Tienen.AMBOS_EQUIPOS;
        }        
        String nombreLance = lance.toString().toLowerCase();
                
        boolean tienen0y2 = false;
        boolean tienen1y3 = false;
        for (int i = 0; i < Constantes.NUMERO_JUGADORES; i++) {
            EstadoJugador estadoJugador = estadoPartida.obtenerEstadoJugador(i);
            boolean tiene = (lance == PARES && estadoJugador.tienePares()) 
                    || (lance == JUEGO && estadoJugador.tieneJuego());            
            if (tiene) {
                if (i % 2 == 0) {
                    tienen0y2 = true;
                } else {
                    tienen1y3 = true;
                }
            }
            agregarMensajesTexto(estadoJugador.getNombreJugador(), (tiene ? "Tengo " : "No tengo ") + nombreLance);
        }
        if (tienen0y2 && tienen1y3) {
            return Tienen.AMBOS_EQUIPOS;
        } else if (tienen0y2) {
            return Tienen.EQUIPO_0Y2;
        } else if (tienen1y3) {
            return Tienen.EQUIPO_1Y3;
        } else {
            return Tienen.NADIE;
        }        
    }
    
    public int normalizarIndiceJugador(int indice) {
        return indice % Constantes.NUMERO_JUGADORES;
    }
    
    private void asignarTurnoSiguienteJugador() throws ExcepcionJuego {        
        int indice = normalizarIndiceJugador(estadoPartida.getIndiceJugadorTurno() + 1);        
        estadoPartida.setIndiceJugadorTurno(indice);
    }
    
    private void asignarTurnoJugadorCompanero() throws ExcepcionJuego {
        int indice = normalizarIndiceJugador(estadoPartida.getIndiceJugadorTurno() + 2);
        estadoPartida.setIndiceJugadorTurno(indice);
    }
    
    private void asignarTurnoSiguienteEquipo() throws ExcepcionJuego {
        asignarTurnoSiguienteJugador();        
        boolean puede = comprobarJugadorTurnoPuedeJugarLance();
        if (!puede) {
            asignarTurnoJugadorCompanero();
        }       
    }
    
    private void accionDarMus(EstadoJugador jugador) throws ExcepcionJuego {
        
        agregarMensajesTexto(jugador.getNombreJugador(), "Mus");
        
        if (estadoPartida.esPostre(jugador)) {
            estadoPartida.setFaseJuego(FaseJuego.DESCARTE);
            asignarTurnoPrimerJugador();
        } else {
            asignarTurnoSiguienteJugador();
        }        
    }
    
    private void accionCortar(EstadoJugador jugador) throws ExcepcionJuego {
        if (estadoPartida.isPrimeraMano()) {
            estadoPartida.setIndiceJugadorMano(jugador.getIndice());
            agregarMensajesTexto(jugador.getNombreJugador(), "Corto y soy mano", 500);
        } else {
            agregarMensajesTexto(jugador.getNombreJugador(), "Corto"); 
        }
        estadoPartida.setIndiceJugadorCorte(jugador.getIndice());                
        iniciarLance(Lance.GRANDES);
    }

    private void accionDescartar(EstadoJugador jugador, int[] indiceDescartes) throws ExcepcionJuego {
        
        if (indiceDescartes == null || indiceDescartes.length == 0) {
            throw new ExcepcionJuego("El jugador " + jugador.getNombreJugador() + " no se ha descartado");
        }
        
        agregarMensajesTexto(jugador.getNombreJugador(), "Descarto " + indiceDescartes.length);
        
        EstadoJugador estadoJugador = estadoPartida.obtenerEstadoJugador(jugador.getNombreJugador());

        List<Naipe> cartasDescartes = new ArrayList<>();
        List<Naipe> cartas = estadoJugador.obtenerListaCartas();
        for (int indice : indiceDescartes) {
            if (indice < 0 || indice >= cartas.size()) {
                throw new ExcepcionJuego("El jugador " + jugador.getNombreJugador() + " no tiene la carta " + indice);
            }
            cartasDescartes.add(cartas.get(indice));
        }
        estadoJugador.descartar(cartasDescartes);
        estadoPartida.agregarDescartes(cartasDescartes);
        estadoJugador.setDescartado(true);
       
        if (estadoPartida.hanDescartadoTodos()) {
            repartirTrasDescarte();
            estadoPartida.setFaseJuego(FaseJuego.DECIDIR_MUS);
            asignarTurnoPrimerJugador();
        } else {
            asignarTurnoSiguienteJugador();
        }
    }
    
    private void accionPasar(EstadoJugador jugador) throws ExcepcionJuego {
        FaseJuego faseJuego = estadoPartida.getFaseJuego();
        if (faseJuego == FaseJuego.APUESTA) {
            if (estadoPartida.esPostre(jugador)) {
                agregarMensajesTexto(jugador.getNombreJugador(), "Se fué");
                finalizarLance();
            } else {
                agregarMensajesTexto(jugador.getNombreJugador(), "Paso");
                asignarTurnoSiguienteJugador();
            }
        } else if (estadoPartida.getFaseJuego() == FaseJuego.RESPUESTA || estadoPartida.getFaseJuego() == FaseJuego.RESPUESTA_ORDAGO) {
            asignarTurnoJugadorCompanero();
            agregarMensajesTexto(jugador.getNombreJugador(), "Paso");
        } else if (estadoPartida.getFaseJuego() == FaseJuego.RESPUESTA_OTRO || estadoPartida.getFaseJuego() == FaseJuego.RESPUESTA_ORDAGO_OTRO) {
            agregarMensajesTexto(jugador.getNombreJugador(), "Se fué");
            finalizarLance();
        }
    }
    
    private void accionEnvidar(EstadoJugador jugador, int apuesta) throws ExcepcionJuego {
        
        agregarMensajesTexto(jugador.getNombreJugador(), "Envido " + apuesta);
        
        estadoPartida.subirApuesta(apuesta);
        estadoPartida.setFaseJuego(FaseJuego.RESPUESTA);
        asignarTurnoSiguienteEquipo();        
    }
    
    private void accionLanzarOrdago(EstadoJugador jugador) throws ExcepcionJuego {
        agregarMensajesTexto(jugador.getNombreJugador(), "¡Órdago!");
        
        estadoPartida.setFaseJuego(FaseJuego.RESPUESTA_ORDAGO);
        asignarTurnoSiguienteJugador();
    }
    
    private void accionQuerer(EstadoJugador jugador) throws ExcepcionJuego {
        agregarMensajesTexto(jugador.getNombreJugador(), "Quiero");
        if (estadoPartida.getFaseJuego() == FaseJuego.RESPUESTA_ORDAGO || estadoPartida.getFaseJuego() == FaseJuego.RESPUESTA_ORDAGO_OTRO) {            
            agregarMensajesTexto(jugador.getNombreJugador(), " ve el órdago en el lance de " + estadoPartida.getLance());
            aceptarOrdago();            
        } else {
            estadoPartida.aceptarApuesta();
            agregarMensajesTexto(null, "Se ven " + estadoPartida.getApuestaAcumulada() + " en el lance de " + estadoPartida.getLance());        
            finalizarLance();
        }
    }

    private void accionNoQuerer(EstadoJugador jugador) throws ExcepcionJuego {
        agregarMensajesTexto(jugador.getNombreJugador(), "No quiero");
        if (estadoPartida.getFaseJuego() == FaseJuego.RESPUESTA) {               
            estadoPartida.setFaseJuego(FaseJuego.RESPUESTA_OTRO);
            asignarTurnoJugadorCompanero();            
        } else if (estadoPartida.getFaseJuego() == FaseJuego.RESPUESTA_OTRO
                || estadoPartida.getFaseJuego() == FaseJuego.RESPUESTA_ORDAGO_OTRO) { 
            equipoRechazaApuesta(jugador);
            finalizarLance();
        } else if (estadoPartida.getFaseJuego() == FaseJuego.RESPUESTA_ORDAGO) { 
            estadoPartida.setFaseJuego(FaseJuego.RESPUESTA_ORDAGO_OTRO);
            asignarTurnoJugadorCompanero();            
        }
    }
    
    private void aceptarOrdago() throws ExcepcionJuego {        
        Lance lance = estadoPartida.getLance();
        EstadoJugador estadoJugadorGanador  = evaluarGanador(lance);
        agregarMensajesTexto(estadoJugadorGanador.getNombreJugador(), "Gano a " + lance);
        String nombreEquipo = estadoPartida.obtenerNombreEquipo(estadoJugadorGanador.getNombreJugador());
        agregarMensajesTexto(null, "Los ganadores por órdago son " + nombreEquipo);
        finalizarJuego();
    }
    
    private void hacerRecuento(Lance lance) throws ExcepcionJuego {
        if ((lance == PARES && !estadoPartida.isHuboPares()) ||
            (lance == JUEGO && !estadoPartida.isHuboJuego())) {
            return;
        }
        int puntos = estadoPartida.obtenerApuestaLance(lance);
        if (puntos > 0) {
            EstadoJugador estadoJugadorGanador = evaluarGanador(lance);
            estadoPartida.sumarPuntos(estadoJugadorGanador, puntos);        
            agregarMensajesTexto(estadoJugadorGanador.getNombreJugador(), " Gano a " + lance + " y sumo " + puntos + " puntos");
        }
    }
    
    private EstadoJugador evaluarGanador(Lance lance) throws ExcepcionJuego {
        Puntuador puntuador;
        switch(lance) {
            case GRANDES:
                puntuador = new PuntuadorGrandes();
                break;
            case CHICAS:
                puntuador = new PuntuadorChicas();
                break;
            case PARES:
                puntuador = new PuntuadorPares();
                break;
            case JUEGO:
            case PUNTO:
                puntuador = new PuntuadorJuego();
                break;            
            default:
                return null;
        }
        
        return evaluarGanador(puntuador);
    }
    
    private EstadoJugador evaluarGanador(Puntuador puntuador) throws ExcepcionJuego {
                         
        EstadoJugador estadoJugadorCandidato = null;
        int puntuacionJugadorCandidato = 0;
        for (EstadoJugador estadoJugador : estadoPartida.getListaEstadosJugadores()) {
            
            estadoJugador.getJugada().comprobarJugadaValida();
            
            int puntuacionJugador = puntuador.calcularPuntuacion(estadoJugador.getJugada());                        
            if (estadoJugadorCandidato == null || puntuacionJugador > puntuacionJugadorCandidato) {
                estadoJugadorCandidato = estadoJugador;
                puntuacionJugadorCandidato = puntuacionJugador;                
            }            
        }
        return estadoJugadorCandidato;        
    }

    
    private void equipoRechazaApuesta(EstadoJugador jugador) throws ExcepcionJuego {
        
        EstadoJugador estadoSiguienteJugador = estadoPartida.obtenerEstadoSiguienteJugador(jugador.getNombreJugador());
        
        int puntos = estadoPartida.getApuestaAcumulada();
        if (puntos == 0) {
            puntos = 1;
        }
        
        estadoPartida.sumarPuntos(estadoSiguienteJugador, puntos);
        String nombreEquipo = estadoPartida.obtenerNombreEquipo(estadoSiguienteJugador.getNombreJugador());
        agregarMensajesTexto(null, nombreEquipo + " ganan " + puntos + " puntos");
        
        estadoPartida.setApuestaAcumulada(0);
        estadoPartida.setApuestaEnvite(0);
    }

    private boolean esTurnoJugador(String nombreJugador) throws ExcepcionJuego {
        return estadoPartida.esTurnoJugador(nombreJugador);
    }

    private boolean sePuedeRealizarAccion(Accion accion) {
        return estadoPartida.sePuedeRealizar(accion);
    }
        
    private void agregarMensajesTurno() throws ExcepcionJuego {
        if (estadoPartida == null) {
            return;
        }               
        for (EstadoJugador estadoJugador : estadoPartida.getListaEstadosJugadores()) {      
            MensajeTurno mensaje = new MensajeTurno();
            mensaje.setNombreJugadorDestinatario(estadoJugador.getNombreJugador());
            mensaje.setNombreJugadorTurno(estadoPartida.obtenerNombreJugadorTurno());            
            mensaje.setFaseJuego(estadoPartida.getFaseJuego());
            mensaje.setLance(estadoPartida.getLance());
            int indiceJugador = estadoJugador.getIndice();
            boolean esIndicePar = indiceJugador % 2 == 0;
            mensaje.setOrden(estadoPartida.obtenerOrdenJugador(indiceJugador));
            mensaje.setHeCortado(estadoPartida.getIndiceJugadorCorte() == estadoJugador.getIndice());
            mensaje.setPuntuacionNosotros(esIndicePar ? estadoPartida.getPuntuacionEquipo0y2() : estadoPartida.getPuntuacionEquipo1y3());
            mensaje.setPuntuacionEllos(!esIndicePar ? estadoPartida.getPuntuacionEquipo0y2() : estadoPartida.getPuntuacionEquipo1y3());
            mensaje.setApuestaAcumulada(estadoPartida.getApuestaAcumulada());
            mensaje.setCartas(estadoJugador.obtenerListaCartas());
            mensaje.setNumeroDescartes(estadoPartida.getNumeroDescartes());
            agregarMensaje(estadoJugador.getNombreJugador(), mensaje);
         }
    
    }
    
    private void agregarMensajesMostrarCartas() throws ExcepcionJuego {
        if (estadoPartida == null) {
            return;
        }               
        for (EstadoJugador estadoJugadorDestinatario : estadoPartida.getListaEstadosJugadores()) {
            for (EstadoJugador estadoJugador : estadoPartida.getListaEstadosJugadores()) {
                MensajeCartas mensaje = new MensajeCartas();
                mensaje.agregarCartasJugador(estadoJugador.getNombreJugador(), estadoJugador.getJugada());
                agregarMensaje(estadoJugadorDestinatario.getNombreJugador(), mensaje);
            }                
        }    
    }   
    
    private void agregarMensajesTexto(String nombreJugador, String texto) {
        agregarMensajesTexto(nombreJugador, texto, 400);
    }
    
    private void agregarMensajesTexto(String nombreJugador, String texto, int retraso) {
        for (EstadoJugador estadoJugador : estadoPartida.getListaEstadosJugadores()) {    
//            if (Objects.equals(estadoJugador.getNombreJugador(), nombreJugador)) {
//                continue;
//            }
            MensajeTexto mensaje = new MensajeTexto();
            mensaje.setNombreJugadorRemitente(nombreJugador);
            mensaje.setNombreJugadorDestinatario(estadoJugador.getNombreJugador());
            mensaje.setTexto(texto);
            mensaje.setMsRetraso(retraso);
            listaMensajes.add(mensaje);
        }
    }

    public List<MensajeJugador> obtenerMensajesAJugadores() {
        return listaMensajes;
    }

}
