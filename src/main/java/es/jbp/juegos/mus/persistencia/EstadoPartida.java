package es.jbp.juegos.mus.persistencia;

import es.jbp.juegos.mus.logica.Constantes;
import es.jbp.juegos.mus.logica.ExcepcionJuego;
import es.jbp.juegos.mus.enumerados.FaseJuego;
import es.jbp.juegos.mus.enumerados.Lance;
import es.jbp.juegos.mus.enumerados.Accion;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 *
 * @author jorge
 */
public class EstadoPartida implements Serializable {

    private Baraja baraja = new Baraja();
    private Lance lance;
    private FaseJuego faseJuego = FaseJuego.DECIDIR_MUS;
    private int indiceJugadorTurno;
    private int indiceJugadorMano;
    private int indiceJugadorCorte;
    private int puntuacionEquipo0y2;
    private int puntuacionEquipo1y3;
    private List<EstadoJugador> listaEstadosJugadores = new ArrayList<>();    
    private int apuestaAcumulada;
    private int apuestaEnvite;
    private boolean empezada;
    private boolean terminada;
    private int apuestaGrandes;
    private int apuestaChicas;
    private int apuestaPares;
    private int apuestaJuego;
    private boolean huboPares;
    private boolean huboJuego;
    private int numeroDescartes;
    private boolean primeraMano;

    public Baraja getBaraja() {
        return baraja;
    }

    public void setBaraja(Baraja baraja) {
        this.baraja = baraja;
    }


    public boolean isEmpezada() {
        return empezada;
    }

    public void setEmpezada(boolean empezada) {
        this.empezada = empezada;
    }

    public boolean isTerminada() {
        return terminada;
    }

    public void setTerminada(boolean terminada) {
        this.terminada = terminada;
    }

    public int getApuestaGrandes() {
        return apuestaGrandes;
    }

    public void setApuestaGrandes(int apuestaGrandes) {
        this.apuestaGrandes = apuestaGrandes;
    }

    public int getApuestaChicas() {
        return apuestaChicas;
    }

    public void setApuestaChicas(int apuestaChicas) {
        this.apuestaChicas = apuestaChicas;
    }

    public int getApuestaPares() {
        return apuestaPares;
    }

    public void setApuestaPares(int apuestaPares) {
        this.apuestaPares = apuestaPares;
    }

    public int getApuestaJuego() {
        return apuestaJuego;
    }

    public void setApuestaJuego(int apuestaJuego) {
        this.apuestaJuego = apuestaJuego;
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

    public List<EstadoJugador> getListaEstadosJugadores() {
        return listaEstadosJugadores;
    }

    public void setListaEstadosJugadores(List<EstadoJugador> listaEstadosJugadores) {
        this.listaEstadosJugadores = listaEstadosJugadores;
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

    public int getIndiceJugadorCorte() {
        return indiceJugadorCorte;
    }

    public void setIndiceJugadorCorte(int indiceJugadorCorte) {
        this.indiceJugadorCorte = indiceJugadorCorte;
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
   
    public boolean isHuboPares() {
        return huboPares;
    }

    public void setHuboPares(boolean huboPares) {
        this.huboPares = huboPares;
    }

    public boolean isHuboJuego() {
        return huboJuego;
    }

    public void setHuboJuego(boolean huboJuego) {
        this.huboJuego = huboJuego;
    }

    public int getNumeroDescartes() {
        return numeroDescartes;
    }

    public void setNumeroDescartes(int numeroDescartes) {
        this.numeroDescartes = numeroDescartes;
    }

    public boolean isPrimeraMano() {
        return primeraMano;
    }

    public void setPrimeraMano(boolean primeraMano) {
        this.primeraMano = primeraMano;
    }

    public Jugada obtenerJugadaDe(String nombreJugador) throws ExcepcionJuego {
        EstadoJugador estadoJugador = obtenerEstadoJugador(nombreJugador);
        return estadoJugador.getJugada();
    }

    public Naipe sacarCarta() throws ExcepcionJuego {
        return baraja.extraerDelMazo();
    }

    private int calcularIndiceSiguienteJugador(int indiceJugador) {
        return (indiceJugador + 1) % Constantes.NUMERO_JUGADORES;
    }

    private int calcularIndiceParejaJugador(int indiceJugador) {
        return (indiceJugador + 2) % Constantes.NUMERO_JUGADORES;
    }
           
    public void barajar() {
        baraja.inicializar();
        baraja.barajarMazo(1000);
    }

    /**
     * Comprueba si es el jugador que le toca realizar una accion
     */
    public boolean esTurnoJugador(String nombreJugador) throws ExcepcionJuego {

        EstadoJugador estadoJugador = obtenerEstadoJugador(nombreJugador);
        return indiceJugadorTurno == estadoJugador.getIndice();
    }

    public boolean sePuedeRealizar(Accion accion) {

        if (faseJuego == null) {
            return false;
        }
        return faseJuego.getAcciones().contains(accion);
    }

    public boolean esPostre(EstadoJugador estadoJugador) throws ExcepcionJuego {
        return calcularIndiceSiguienteJugador(estadoJugador.getIndice()) == getIndiceJugadorMano();
    }

    public void agregarDescartes(List<Naipe> cartasDescartes) {
        baraja.agregarDescartes(cartasDescartes);
    }

    public void subirApuesta(int apuesta) {
        apuestaAcumulada += apuestaEnvite;
        apuestaEnvite = apuesta;
    }

    public void aceptarApuesta() {
        apuestaAcumulada += apuestaEnvite;
        apuestaEnvite = 0;
        asignarApuestaLance(lance, apuestaAcumulada);
    }  

    public void sumarPuntos(EstadoJugador estadoJugador, int puntos) {
        int indiceJugador = estadoJugador.getIndice();
        if (indiceJugador == 0 || indiceJugador == 2) {
            puntuacionEquipo0y2 += puntos;
        } else {
            puntuacionEquipo1y3 += puntos;
        }
    }

    public EstadoJugador obtenerEstadoSiguienteJugador(String nombre) throws ExcepcionJuego {
        EstadoJugador estadoJugador = obtenerEstadoJugador(nombre);
        int indice = calcularIndiceSiguienteJugador(estadoJugador.getIndice());
        return obtenerEstadoJugador(indice);
    }

    public String obtenerNombreEquipo(int indice) throws ExcepcionJuego {
        EstadoJugador estadoJugador = obtenerEstadoJugador(indice);
        int indicePareja = calcularIndiceParejaJugador(estadoJugador.getIndice());
        EstadoJugador estadoJugadorPareja = obtenerEstadoJugador(indicePareja);
        return obtenerNombreEquipo(estadoJugador, estadoJugadorPareja);
    }

    public String obtenerNombreEquipo(String nombre) throws ExcepcionJuego {
        return obtenerNombreEquipo(obtenerIndiceJugador(nombre));
    }

    private String obtenerNombreEquipo(EstadoJugador estadoJugador1, EstadoJugador estadoJugador2) {
        if (estadoJugador1.getIndice() < estadoJugador2.getIndice()) {
            return estadoJugador1.getNombreJugador() + " y " + estadoJugador2.getNombreJugador();
        } else {
            return estadoJugador2.getNombreJugador() + " y " + estadoJugador1.getNombreJugador();
        }
    }

    public String obtenerNombreEquipoGanador() throws ExcepcionJuego {
        if (puntuacionEquipo0y2 >= Constantes.NUMERO_PUNTOS_POR_PARTIDA) {
            return obtenerNombreEquipo(obtenerEstadoJugador(0), obtenerEstadoJugador(1));
        } else if (puntuacionEquipo1y3 >= Constantes.NUMERO_PUNTOS_POR_PARTIDA) {
            return obtenerNombreEquipo(obtenerEstadoJugador(1), obtenerEstadoJugador(3));
        } else {
            return null;
        }
    }

    public boolean hayEquipoGanador() {
        return puntuacionEquipo0y2 >= Constantes.NUMERO_PUNTOS_POR_PARTIDA
                || puntuacionEquipo1y3 >= Constantes.NUMERO_PUNTOS_POR_PARTIDA;
    }

    private void asignarApuestaLance(Lance lance, int apuesta) {
        switch(lance) {
            case GRANDES:
                apuestaGrandes = apuesta;
                break;
            case CHICAS:
                apuestaChicas = apuesta;
                break;
            case PARES:
                apuestaPares = apuesta;
                break;
            case JUEGO:
            case PUNTO:
                apuestaJuego = apuesta;
                break;
        } 
    }

    public int obtenerApuestaLance(Lance lance) {
        if (lance == null) {
            return 0;
        }
        switch(lance) {
            case GRANDES:
                return apuestaGrandes;
            case CHICAS:
                return apuestaChicas;
            case PARES:
                return apuestaPares;
            case JUEGO:
            case PUNTO:
                return apuestaJuego;           
            default:
                return 0;
        }
    }
        
    public EstadoJugador obtenerEstadoJugador(String nombreJugador) throws ExcepcionJuego {
        if (nombreJugador == null) {
            throw new ExcepcionJuego("No ha indicado el jugador");
        }
        Optional<EstadoJugador> optEstadoJugador = listaEstadosJugadores.stream().filter(estadoJugador -> nombreJugador.equals(estadoJugador.getNombreJugador())).findFirst();
        if (!optEstadoJugador.isPresent() || optEstadoJugador.get() == null) {
            throw new ExcepcionJuego("El jugador " + nombreJugador + " no tiene estado");
        }                
        return optEstadoJugador.get();
    }
    
    public EstadoJugador obtenerEstadoJugador(int indice) throws ExcepcionJuego {
        Optional<EstadoJugador> optEstadoJugador = listaEstadosJugadores.stream().filter(estadoJugador -> indice == estadoJugador.getIndice()).findFirst();
        if (!optEstadoJugador.isPresent() || optEstadoJugador.get() == null) {
            throw new ExcepcionJuego("El jugador " + indice + " no tiene estado");
        }                
        return optEstadoJugador.get();
    }
    
    public EstadoJugador obtenerEstadoJugadorTurno() throws ExcepcionJuego {
        return obtenerEstadoJugador(getIndiceJugadorTurno());
    }
    
    public void agregarJugador(String nombre) {
        int indice = listaEstadosJugadores.size();
        listaEstadosJugadores.add(new EstadoJugador(nombre, indice));
    }

    public void recojerCartasJugadores() {        
        listaEstadosJugadores.stream().forEach(estadoJugador -> estadoJugador.recojerCartas());
    }

    public boolean hanDescartadoTodos() {
        return listaEstadosJugadores.stream().allMatch(estado -> estado.isDescartado());
    }
    
    public void ordenarCartasJugadores() {
        listaEstadosJugadores.forEach(estado -> estado.ordenarCartas());
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 89 * hash + Objects.hashCode(this.baraja);
        hash = 89 * hash + Objects.hashCode(this.lance);
        hash = 89 * hash + Objects.hashCode(this.faseJuego);
        hash = 89 * hash + this.indiceJugadorTurno;
        hash = 89 * hash + this.indiceJugadorMano;
        hash = 89 * hash + this.puntuacionEquipo0y2;
        hash = 89 * hash + this.puntuacionEquipo1y3;
        hash = 89 * hash + Objects.hashCode(this.listaEstadosJugadores);
        hash = 89 * hash + this.apuestaAcumulada;
        hash = 89 * hash + this.apuestaEnvite;
        hash = 89 * hash + (this.empezada ? 1 : 0);
        hash = 89 * hash + (this.terminada ? 1 : 0);
        hash = 89 * hash + this.apuestaGrandes;
        hash = 89 * hash + this.apuestaChicas;
        hash = 89 * hash + this.apuestaPares;
        hash = 89 * hash + this.apuestaJuego;
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
        final EstadoPartida other = (EstadoPartida) obj;
        if (this.indiceJugadorTurno != other.indiceJugadorTurno) {
            return false;
        }
        if (this.indiceJugadorMano != other.indiceJugadorMano) {
            return false;
        }
        if (this.puntuacionEquipo0y2 != other.puntuacionEquipo0y2) {
            return false;
        }
        if (this.puntuacionEquipo1y3 != other.puntuacionEquipo1y3) {
            return false;
        }
        if (this.apuestaAcumulada != other.apuestaAcumulada) {
            return false;
        }
        if (this.apuestaEnvite != other.apuestaEnvite) {
            return false;
        }
        if (this.empezada != other.empezada) {
            return false;
        }
        if (this.terminada != other.terminada) {
            return false;
        }
        if (this.apuestaGrandes != other.apuestaGrandes) {
            return false;
        }
        if (this.apuestaChicas != other.apuestaChicas) {
            return false;
        }
        if (this.apuestaPares != other.apuestaPares) {
            return false;
        }
        if (this.apuestaJuego != other.apuestaJuego) {
            return false;
        }
        if (!Objects.equals(this.baraja, other.baraja)) {
            return false;
        }
        if (this.lance != other.lance) {
            return false;
        }
        if (this.faseJuego != other.faseJuego) {
            return false;
        }
        if (!Objects.equals(this.listaEstadosJugadores, other.listaEstadosJugadores)) {
            return false;
        }
        return true;
    }

    public int obtenerNumeroJugadores() {
        return listaEstadosJugadores.size();
    }

    public Integer obtenerIndiceJugador(String nombreJugador) {
        return listaEstadosJugadores.stream()
                .filter(estadoJugador -> nombreJugador.equals(estadoJugador.getNombreJugador()))
                .map(estadoJugador -> estadoJugador.getIndice())
                .findFirst()
                .orElse(null);
    }

    public String obtenerNombreJugadorTurno() {
        return listaEstadosJugadores.stream()
                .filter(estadoJugador -> estadoJugador.getIndice() == indiceJugadorTurno)
                .map(estadoJugador -> estadoJugador.getNombreJugador())
                .findFirst()
                .orElse(null);
    }
    
    public String obtenerNombreJugadorMano() {
        return listaEstadosJugadores.stream()
                .filter(estadoJugador -> estadoJugador.getIndice() == indiceJugadorMano)
                .map(estadoJugador -> estadoJugador.getNombreJugador())
                .findFirst()
                .orElse(null);
    }

    public int obtenerOrdenJugador(int indiceJugador) {
        int orden = indiceJugadorMano - indiceJugador;
        return orden >= 0 ? orden : 4 - orden;               
    }    

}
