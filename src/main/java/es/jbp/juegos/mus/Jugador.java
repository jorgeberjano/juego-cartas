
package es.jbp.juegos.mus;

import java.util.List;

/**
 *
 * @author jorge
 */
public interface Jugador {

    public String getNombre();
    
    void mensaje(String mensaje);
    
    void informacion(String mensaje);
    
    void turno(Lance lance, FaseJuego estadoLance);    

    int[] getDescartes();

    int getApuesta();

    void setCartas(List<Naipe> cartas);
    
    void setPuntos(int puntos);
}
