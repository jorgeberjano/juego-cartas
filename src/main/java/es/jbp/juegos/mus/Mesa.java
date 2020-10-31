package es.jbp.juegos.mus;

import es.jbp.juegos.mus.puntuadores.Puntuador;
import es.jbp.juegos.mus.puntuadores.PuntuadorChicas;
import es.jbp.juegos.mus.puntuadores.PuntuadorGrandes;
import es.jbp.juegos.mus.puntuadores.PuntuadorJuego;
import es.jbp.juegos.mus.puntuadores.PuntuadorPares;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jorge
 */
public class Mesa {

    private EstadoPartida estadoPartida = new EstadoPartida();
    private List<Jugador> jugadores = new ArrayList<>();

    public void agregarJugador(Jugador jugador, int indice) {
        jugadores.add(jugador);
        estadoPartida.agregarJugador(jugador, indice);
    }

    public void repartir() throws ExcepcionJuego {
        estadoPartida.recojerCartas();
        // Se reparten las cartas a cada jugador
        for (int i = 0; i < Constantes.NUMERO_CARTAS_POR_JUGADOR; i++) {
            for (Jugador jugador : jugadores) {
                estadoPartida.asignarCarta(jugador.getNombre(), estadoPartida.sacarCarta());
            }
        }
        
        estadoPartida.ordenarCartas();
        
        for (Jugador jugador : jugadores) {
            jugador.setCartas(estadoPartida.getCartasJugador(jugador));
        }
        
         
    }

    public void repartirTrasDescarte() throws ExcepcionJuego {
        for (Jugador jugador : jugadores) {
            EstadoJugador estadoJugador = estadoPartida.getEstadoJugador(jugador.getNombre());
            int numeroCartas = estadoJugador.getCartas().size();
            for (int i = numeroCartas; i < Constantes.NUMERO_CARTAS_POR_JUGADOR; i++) {
                estadoPartida.asignarCarta(jugador.getNombre(), estadoPartida.sacarCarta());
            }                     
        }
        
        estadoPartida.ordenarCartas();
        
        for (Jugador jugador : jugadores) {
            jugador.setCartas(estadoPartida.getCartasJugador(jugador));
        }
    }

    public void iniciarPartida(int indiceJugadorMano) throws ExcepcionJuego {
        estadoPartida.iniciarPartida();
        estadoPartida.setIndiceJugadorMano(indiceJugadorMano);
        iniciarMano();
    }
       
    private void iniciarMano() throws ExcepcionJuego {
        estadoPartida.iniciarMano();

        repartir();

        estadoPartida.setFaseJuego(FaseJuego.DECIDIR_MUS);
        asignarTurnoJugadorMano(); 
        pedirAccionJugador();        
    }
    
    public void pedirAccionJugador() throws ExcepcionJuego {
        for (Jugador jugador : jugadores) {
            
            if (estadoPartida.haTerminado()) {
                jugador.mensaje("La partida ha terminado");
                continue;
            }

            if (!estadoPartida.esTurnoJugador(jugador)) {
                jugador.mensaje("Espere...");
                continue;
            }
            Lance lance = estadoPartida.getLance();
            if (lance == null) {
                jugador.mensaje(estadoPartida.getFaseJuego().toString());
            } else {
                jugador.mensaje(estadoPartida.getLance() + ": " + estadoPartida.getFaseJuego());
            }
        }
    }
    
    private void iniciarLance(Lance lance) {
        estadoPartida.setApuestaAcumulada(0);
        estadoPartida.setIndiceJugadorTurno(estadoPartida.getIndiceJugadorMano());
        estadoPartida.setLance(lance);
        estadoPartida.setFaseJuego(FaseJuego.APUESTA);
        informar("Lance de " + lance);
    }
   
    private void finalizarLance() throws ExcepcionJuego {
        
        informarPuntuacion();
        
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
                finalizarMano();
                return;
        }        
        iniciarLance(lance);
    }
    
    private void finalizarMano() throws ExcepcionJuego {        
        informar("La mano ha finalizado");
        
        for (Lance lance : Lance.values()) {
            hacerRecuento(lance);
            if (estadoPartida.hayEquipoGanador()) {
                String nombreEquipoGanador = estadoPartida.getNombreEquipoGanador();
                informar("Los ganadores por puntos son " + nombreEquipoGanador);
                finalizarPartida();
                return;
            }
        }
        
        informarPuntuacion();
                
        estadoPartida.incrementarIndiceJugadorMano();
        iniciarMano();
    }
    
    private void finalizarPartida() throws ExcepcionJuego, ExcepcionJuego {
        estadoPartida.terminarPartida();
        informar("La partida ha finalizado");        
    }
       
    public void asignarManoPrimerJugador() {
         estadoPartida.setIndiceJugadorMano(0);
    }
    
    public void asignarTurnoJugadorMano() {
        estadoPartida.setIndiceJugadorTurno(estadoPartida.getIndiceJugadorMano());
    }

    public void asignarTurnoSiguienteJugador() {        
        estadoPartida.incrementarIndiceJugadorTurno(1);
    }

    public Baraja getMazo() {
        return estadoPartida.getMazo();
    }

    public void accionJugador(Jugador jugador, Accion accion) throws ExcepcionJuego {
        
        if (!estadoPartida.haEmpezado()) {
            jugador.mensaje("La partida no ha empezado");
            return;
        } else if (estadoPartida.haTerminado()) {
            jugador.mensaje("La partida ha terminado");
            return;
        }
        
        if (!esTurnoJugador(jugador)) {
            jugador.mensaje("No es tu turno");
            return;
        }

        if (!sePuedeRealizarAccion(accion)) {
            jugador.mensaje("No se puede " + accion + " en este momento");
        }

        realizarAccion(jugador, accion);
        
        pedirAccionJugador();  
    }

    private void realizarAccion(Jugador jugador, Accion accion) throws ExcepcionJuego {
       
        switch (accion) {
            case DAR_MUS:
                accionDarMus(jugador);
                break;
            case CORTAR:
                accionCortar(jugador);
                break;
            case DESCARTAR:
                accionDescartar(jugador);
                break;
            case PASAR:
                accionPasar(jugador);
                break;
            case ENVIDAR:
                accionEnvidar(jugador);
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
    }

    private void accionDarMus(Jugador jugador) throws ExcepcionJuego {
        if (estadoPartida.esPostre(jugador)) {
            estadoPartida.setFaseJuego(FaseJuego.DESCARTE);
            asignarTurnoJugadorMano();
        } else {
            asignarTurnoSiguienteJugador();
        }
        informar(jugador.getNombre() + ": mus");
    }
    
    private void accionCortar(Jugador jugador) throws ExcepcionJuego {
        informar(jugador.getNombre() + ": corta");
        asignarTurnoJugadorMano(); 
        iniciarLance(Lance.GRANDES);
    }

    private void accionDescartar(Jugador jugador) throws ExcepcionJuego {
        int[] indiceDescartes = jugador.getDescartes();
        EstadoJugador estadoJugador = estadoPartida.getEstadoJugador(jugador.getNombre());

        List<Naipe> cartasDescartes = new ArrayList<>();
        List<Naipe> cartas = estadoJugador.getCartas();
        for (int indice : indiceDescartes) {
            if (indice < 0 || indice >= cartas.size()) {
                throw new ExcepcionJuego("El jugador no tiene la carta " + indice);
            }
            cartasDescartes.add(cartas.get(indice));
        }
        estadoJugador.descartar(cartasDescartes);
        estadoPartida.agregarDescartes(cartasDescartes);
        estadoJugador.setDescartado(true);

        informar(jugador.getNombre() + ": descarta " + indiceDescartes.length + " naipes");
        
        if (estadoPartida.hanDescartadoTodos()) {
            repartirTrasDescarte();
            estadoPartida.setFaseJuego(FaseJuego.DECIDIR_MUS);
            asignarTurnoJugadorMano();
        }
    }
    
    private void accionPasar(Jugador jugador) throws ExcepcionJuego {
        informar(jugador.getNombre() + ": paso");
        estadoPartida.incrementarIndiceJugadorTurno(1); 
        if (estadoPartida.esPostre(jugador)) {
            informar("Se fué");
            finalizarLance();
        }
    }
    
    private void accionEnvidar(Jugador jugador) throws ExcepcionJuego {
        int apuesta = jugador.getApuesta();
        estadoPartida.subirApuesta(apuesta);
        informar(jugador.getNombre() + ": envida " + apuesta);
        estadoPartida.incrementarIndiceJugadorTurno(1);        
        estadoPartida.setFaseJuego(FaseJuego.RESPUESTA); 
        
    }
    
    private void accionLanzarOrdago(Jugador jugador) {
        informar(jugador.getNombre() + ": ORDAGO!");      
        estadoPartida.incrementarIndiceJugadorTurno(1); 
        estadoPartida.setFaseJuego(FaseJuego.RESPUESTA_ORDAGO);           
    }
    
    private void accionQuerer(Jugador jugador) throws ExcepcionJuego {
        informar(jugador.getNombre() + ": quiero");
        if (estadoPartida.getFaseJuego() == FaseJuego.RESPUESTA_ORDAGO || estadoPartida.getFaseJuego() == FaseJuego.RESPUESTA_ORDAGO_OTRO) {            
            informar(jugador.getNombre() + " ve el órdago en el lance de " + estadoPartida.getLance());
            aceptarOrdago();            
        } else {
            estadoPartida.aceptarApuesta();
            informar("Se ven " + estadoPartida.getApuestaAcumulada() + " en el lance de " + estadoPartida.getLance());        
            finalizarLance();
        }
    }

    private void accionNoQuerer(Jugador jugador) throws ExcepcionJuego {
        informar(jugador.getNombre() + ": no quiero");
        if (estadoPartida.getFaseJuego() == FaseJuego.RESPUESTA) {               
            estadoPartida.setFaseJuego(FaseJuego.RESPUESTA_OTRO);
            estadoPartida.incrementarIndiceJugadorTurno(2);
        } else if (estadoPartida.getFaseJuego() == FaseJuego.RESPUESTA_OTRO
                || estadoPartida.getFaseJuego() == FaseJuego.RESPUESTA_ORDAGO_OTRO) { 
            equipoRechazaApuesta(jugador);
            finalizarLance();
        } else if (estadoPartida.getFaseJuego() == FaseJuego.RESPUESTA_ORDAGO) { 
            estadoPartida.setFaseJuego(FaseJuego.RESPUESTA_ORDAGO_OTRO);
            estadoPartida.incrementarIndiceJugadorTurno(2);
        }
    }
    
    private void aceptarOrdago() throws ExcepcionJuego {
        Lance lance = estadoPartida.getLance();
        EstadoJugador estadoJugadorGanador  = evaluarGanador(lance);
        informar(estadoJugadorGanador.getNombre() + " gana a " + lance);
        String nombreEquipo = estadoPartida.getNombreEquipo(estadoJugadorGanador.getNombre());
        informar("Los ganadores por órdago son " + nombreEquipo);
        finalizarPartida();
    }
    
    private void hacerRecuento(Lance lance) throws ExcepcionJuego {                        
        int puntos = estadoPartida.getApuestaLance(lance);
        if (puntos > 0) {
            EstadoJugador estadoJugadorGanador  = evaluarGanador(lance);
            estadoPartida.sumarPuntos(estadoJugadorGanador, puntos);        
            informar(estadoJugadorGanador.getNombre() + " gana " + lance + " y suma " + puntos + " puntos");
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
                puntuador = new PuntuadorJuego();
                break;
            default:
                return null;
        }
        
        return evaluarGanador(puntuador);
    }
    
    public EstadoJugador evaluarGanador(Puntuador puntuador) throws ExcepcionJuego {
                         
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

    
    private void equipoRechazaApuesta(Jugador jugador) throws ExcepcionJuego {
        
        EstadoJugador estadoSiguienteJugador = estadoPartida.getEstadoSiguienteJugador(jugador.getNombre());
        
        int puntos = estadoPartida.getApuestaAcumulada();
        if (puntos == 0) {
            puntos = 1;
        }
        
        estadoPartida.sumarPuntos(estadoSiguienteJugador, puntos);
        String nombreEquipo = estadoPartida.getNombreEquipo(estadoSiguienteJugador.getNombre());
        informar(nombreEquipo + " ganan " + puntos + " puntos");
        
        estadoPartida.setApuestaAcumulada(0);
        estadoPartida.setApuestaEnvite(0);
    }

    public boolean esTurnoJugador(Jugador jugador) throws ExcepcionJuego {
        return estadoPartida.esTurnoJugador(jugador);
    }

    public boolean sePuedeRealizarAccion(Accion accion) {
        return estadoPartida.sePuedeRealizar(accion);
    }

    private void informar(String info) {
        jugadores.stream().forEach(jugador -> jugador.informacion(info));
    }

    private void informarPuntuacion() throws ExcepcionJuego {        
        for (Jugador jugador : jugadores) {
            jugador.setPuntos(getPuntosJugador(jugador));
        }
    }
    
    private int getPuntosJugador(Jugador jugador) throws ExcepcionJuego {
        EstadoJugador estadoJugador = estadoPartida.getEstadoJugador(jugador.getNombre());
        int indice = estadoJugador.getIndice();
        if (indice == 0 || indice == 2) {
            return estadoPartida.getPuntuacionEquipo0y2();
        } else {
            return estadoPartida.getPuntuacionEquipo1y3();
        }
    }



}
