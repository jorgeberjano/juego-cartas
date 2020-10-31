package es.jbp.juegos.mus;

import java.util.List;

/**
 *
 * @author jorge
 */
public class EstadoJugador {
    private String nombre;
    private int indice;
    private Jugada jugada = new Jugada();
    private boolean descartado;

    public EstadoJugador(String nombre, int indice) {
        this.nombre = nombre;
        this.indice = indice;
    }
    
    public int getIndice() {
        return indice;
    }

    public void setIndice(int indice) {
        this.indice = indice;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Jugada getJugada() {
        return jugada;
    }

    public void setJugada(Jugada jugada) {
        this.jugada = jugada;
    }
    
    public boolean isDescartado() {
        return descartado;
    }

    public void setDescartado(boolean descartado) {
        this.descartado = descartado;
    }

    public void asignarCarta(Naipe carta) {
        this.jugada.agregar(carta);
    }

    public void recojerCartas() {
        jugada.limpiar();
    }
    
    public void ordenarCartas() {
        jugada.ordenar();
    }


    public List<Naipe> getCartas() {
        return jugada.getCartas();
    }

    public void descartar(List<Naipe> descartes) throws ExcepcionJuego {
        jugada.descartar(descartes);
    }

    public void comprobarJugadaValida() throws ExcepcionJuego {
        jugada.comprobarJugadaValida();
    }

}
