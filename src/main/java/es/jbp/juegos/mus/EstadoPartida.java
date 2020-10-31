package es.jbp.juegos.mus;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author jorge
 */
public class EstadoPartida {

    private Baraja mazo = new Baraja();
    private Lance lance;
    private FaseJuego faseJuego;
    private int indiceJugadorTurno;
    private int indiceJugadorMano;
    private int puntuacionEquipo0y2;
    private int puntuacionEquipo1y3;
    private EstadoJugadores estadoJugadores = new EstadoJugadores();
    private int apuestaAcumulada;
    private int apuestaEnvite;
    private boolean empezada;
    private boolean terminada;
    private Map<Lance, Integer> apuestasLances = new HashMap<>();

    public Baraja getMazo() {
        return mazo;
    }

        
    public void iniciarPartida() {
        empezada = true;
        terminada = false;
    }            
    
    public void terminarPartida() {
        terminada = true;
        setFaseJuego(null); 
        setLance(null);
    } 
    
    public boolean haEmpezado() {
        return empezada;
    }

    public boolean haTerminado() {
        return terminada;
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

    public void setPuntuacionEquipo0y2(int puntuacionEquipo0y2) {
        this.puntuacionEquipo0y2 = puntuacionEquipo0y2;
    }

    public void setPuntuacionEquipo1y3(int puntuacionEquipo1y3) {
        this.puntuacionEquipo1y3 = puntuacionEquipo1y3;
    }

    public int getPuntuacionEquipo0y2() {
        return puntuacionEquipo0y2;
    }

    public int getPuntuacionEquipo1y3() {
        return puntuacionEquipo1y3;
    }

    public void setIndiceJugadorTurno(int indiceJugadorTurno) {
        this.indiceJugadorTurno = indiceJugadorTurno;
    }

    public int getIndiceJugadorTurno() {
        return indiceJugadorTurno;
    }

    public int getIndiceJugadorMano() {
        return indiceJugadorMano;
    }

    public void setIndiceJugadorMano(int indiceJugadorMano) {
        this.indiceJugadorMano = indiceJugadorMano;
    }

    public int getApuestaAcumulada() {
        return apuestaAcumulada;
    }

    public void setApuestaAcumulada(int apuestaAcumulada) {
        this.apuestaAcumulada = apuestaAcumulada;
    }

    public int getApuestaEnvite() {
        return apuestaEnvite;
    }

    public void setApuestaEnvite(int apuestaEnvite) {
        this.apuestaEnvite = apuestaEnvite;
    }

    public int getApuestaLance(Lance lance) {
        Integer apuesta = apuestasLances.get(lance);
        return apuesta == null ? 0 : apuesta;
    }

    public void setApuestaLance(Lance lance, int apuesta) {
        apuestasLances.put(lance, apuesta);
    }

    public EstadoJugador getEstadoJugador(String nombreJugador) throws ExcepcionJuego {
        if (nombreJugador == null) {
            throw new ExcepcionJuego("No ha indicado el jugador");
        }
        EstadoJugador estadoJugador = estadoJugadores.getEstadoJugador(nombreJugador);
        if (estadoJugador == null) {
            throw new ExcepcionJuego("El jugador no tiene estado");
        }
        return estadoJugador;
    }

    public List<Naipe> getCartasJugador(Jugador jugador) throws ExcepcionJuego {
        return getJugadaDe(jugador).getCartas();
    }

    private Jugada getJugadaDe(Jugador jugador) throws ExcepcionJuego {
        EstadoJugador estadoJugador = getEstadoJugador(jugador.getNombre());
        if (estadoJugador == null) {
            System.err.println("El jugador no tiene estado");
            return null;
        }
        return estadoJugador.getJugada();
    }

    public void asignarCarta(String nombreJugador, Naipe carta) throws ExcepcionJuego {
        getEstadoJugador(nombreJugador).asignarCarta(carta);
    }

    public Naipe sacarCarta() throws ExcepcionJuego {
        return mazo.extraerDelMazo();
    }

    public void agregarJugador(Jugador jugador, int indice) {
        estadoJugadores.agregarJugador(jugador.getNombre(), indice);
    }

    public void recojerCartas() {
        estadoJugadores.recojerCartas();
    }

    private int calcularIndiceSiguienteJugador(int indiceJugador) {
        return (indiceJugador + 1) % Constantes.NUMERO_JUGADORES;
    }

    private int calcularIndiceParejaJugador(int indiceJugador) {
        return (indiceJugador + 2) % Constantes.NUMERO_JUGADORES;
    }
           

    public void iniciarMano() {
        setLance(null);
        mazo.inicializar();
        mazo.barajarMazo(1000);
    }

    /**
     * Comprueba si es el jugador que le toca el turno
     */
    public boolean esTurnoJugador(Jugador jugador) throws ExcepcionJuego {

        EstadoJugador estadoJugador = getEstadoJugador(jugador.getNombre());

        if (faseJuego == FaseJuego.DESCARTE) {
            return !estadoJugador.isDescartado();
        }

        return indiceJugadorTurno == estadoJugador.getIndice();
    }

    public boolean sePuedeRealizar(Accion accion) {

        if (faseJuego == null) {
            return false;
        }
        return faseJuego.getAcciones().contains(accion);
    }

    public boolean esPostre(Jugador jugador) throws ExcepcionJuego {
        EstadoJugador estadoJugador = getEstadoJugador(jugador.getNombre());
        return calcularIndiceSiguienteJugador(estadoJugador.getIndice()) == getIndiceJugadorMano();
    }

    public void agregarDescartes(List<Naipe> cartasDescartes) {
        mazo.agregarDescartes(cartasDescartes);
    }

    public boolean hanDescartadoTodos() {
        return estadoJugadores.hanDescartadoTodos();
    }

    public void subirApuesta(int apuesta) {
        apuestaAcumulada += apuestaEnvite;
        apuestaEnvite = apuesta;
    }

    public void aceptarApuesta() {
        apuestaAcumulada += apuestaEnvite;
        apuestaEnvite = 0;
        setApuestaLance(lance, apuestaAcumulada);
    }

    public void incrementarIndiceJugadorTurno(int incremento) {
        setIndiceJugadorTurno((getIndiceJugadorTurno() + incremento) % Constantes.NUMERO_JUGADORES);
    }

    public void incrementarIndiceJugadorMano() {
        setIndiceJugadorMano((getIndiceJugadorMano() + 1) % Constantes.NUMERO_JUGADORES);
    }

    public void sumarPuntos(EstadoJugador estadoJugador, int puntos) {
        int indiceJugador = estadoJugador.getIndice();
        if (indiceJugador == 0 || indiceJugador == 2) {
            puntuacionEquipo0y2 += puntos;
        } else {
            puntuacionEquipo1y3 += puntos;
        }
    }

    public EstadoJugador getEstadoSiguienteJugador(String nombre) throws ExcepcionJuego {
        EstadoJugador estadoJugador = getEstadoJugador(nombre);
        int indice = calcularIndiceSiguienteJugador(estadoJugador.getIndice());
        return estadoJugadores.getEstadoJugador(indice);
    }

    public void ordenarCartas() {
        estadoJugadores.ordenarCartas();
    }

    public String getNombreEquipo(String nombre) throws ExcepcionJuego {
        EstadoJugador estadoJugador = getEstadoJugador(nombre);
        int indice = calcularIndiceParejaJugador(estadoJugador.getIndice());
        EstadoJugador estadoJugadorPareja = estadoJugadores.getEstadoJugador(indice);
        return getNombreEquipo(estadoJugador, estadoJugadorPareja);
    }

    private String getNombreEquipo(EstadoJugador estadoJugador1, EstadoJugador estadoJugador2) {
        if (estadoJugador1.getIndice() < estadoJugador2.getIndice()) {
            return estadoJugador1.getNombre() + " y " + estadoJugador2.getNombre();
        } else {
            return estadoJugador2.getNombre() + " y " + estadoJugador1.getNombre();
        }
    }

    public String getNombreEquipoGanador() throws ExcepcionJuego {
        if (puntuacionEquipo0y2 >= Constantes.NUMERO_PUNTOS_POR_PARTIDA) {
            return getNombreEquipo(estadoJugadores.getEstadoJugador(0), estadoJugadores.getEstadoJugador(1));
        } else if (puntuacionEquipo1y3 >= Constantes.NUMERO_PUNTOS_POR_PARTIDA) {
            return getNombreEquipo(estadoJugadores.getEstadoJugador(1), estadoJugadores.getEstadoJugador(3));
        } else {
            return null;
        }
    }

    public boolean hayEquipoGanador() {
        return puntuacionEquipo0y2 >= Constantes.NUMERO_PUNTOS_POR_PARTIDA
                || puntuacionEquipo1y3 >= Constantes.NUMERO_PUNTOS_POR_PARTIDA;
    }

    public List<EstadoJugador> getListaEstadosJugadores() {
        return estadoJugadores.getListaEstadosJugadores();
    }

}
