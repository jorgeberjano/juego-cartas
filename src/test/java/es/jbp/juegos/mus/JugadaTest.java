package es.jbp.juegos.mus;

import es.jbp.juegos.mus.puntuadores.Puntuador;
import es.jbp.juegos.mus.puntuadores.PuntuadorChicas;
import es.jbp.juegos.mus.puntuadores.PuntuadorGrandes;
import es.jbp.juegos.mus.puntuadores.PuntuadorJuego;
import es.jbp.juegos.mus.puntuadores.PuntuadorPares;
import org.junit.Assert;
import static org.junit.Assert.assertTrue;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author jorge
 */
public class JugadaTest {

    public JugadaTest() {
    }

    static Jugada jugada1 = new Jugada();

    static Jugada jugada2 = new Jugada();

    static Jugada jugada3 = new Jugada();

    static Jugada jugada4 = new Jugada();

    static Jugada jugada5 = new Jugada();
    
    static Jugada jugada6 = new Jugada();
    
    static Jugada jugada7 = new Jugada();
    
    
    static Puntuador puntuadorGrandes;
    static Puntuador puntuadorChicas;
    static Puntuador puntuadorPares;
    static Puntuador puntuadorJuego;

    @BeforeClass
    public static void setUpClass() {
        
        puntuadorGrandes = new PuntuadorGrandes();
        puntuadorChicas = new PuntuadorChicas();
        puntuadorPares = new PuntuadorPares();
        puntuadorJuego = new PuntuadorJuego();

        jugada1.agregar(new Naipe(9, Palo.BASTOS));
        jugada1.agregar(new Naipe(9, Palo.BASTOS));
        jugada1.agregar(new Naipe(2, Palo.BASTOS));
        jugada1.agregar(new Naipe(1, Palo.BASTOS));

        jugada2.agregar(new Naipe(10, Palo.BASTOS));
        jugada2.agregar(new Naipe(10, Palo.BASTOS));
        jugada2.agregar(new Naipe(3, Palo.BASTOS));
        jugada2.agregar(new Naipe(1, Palo.BASTOS));

        jugada3.agregar(new Naipe(8, Palo.BASTOS));
        jugada3.agregar(new Naipe(8, Palo.BASTOS));
        jugada3.agregar(new Naipe(8, Palo.BASTOS));
        jugada3.agregar(new Naipe(7, Palo.BASTOS));

        jugada4.agregar(new Naipe(9, Palo.BASTOS));
        jugada4.agregar(new Naipe(9, Palo.BASTOS));
        jugada4.agregar(new Naipe(1, Palo.BASTOS));
        jugada4.agregar(new Naipe(1, Palo.BASTOS));

        jugada5.agregar(new Naipe(9, Palo.BASTOS));
        jugada5.agregar(new Naipe(7, Palo.BASTOS));
        jugada5.agregar(new Naipe(7, Palo.BASTOS));
        jugada5.agregar(new Naipe(2, Palo.BASTOS));

        jugada6.agregar(new Naipe(7, Palo.BASTOS));
        jugada6.agregar(new Naipe(6, Palo.BASTOS));
        jugada6.agregar(new Naipe(5, Palo.BASTOS));
        jugada6.agregar(new Naipe(4, Palo.BASTOS));
        
        jugada7.agregar(new Naipe(10, Palo.BASTOS));
        jugada7.agregar(new Naipe(10, Palo.BASTOS));
        jugada7.agregar(new Naipe(10, Palo.BASTOS));
        jugada7.agregar(new Naipe(3, Palo.BASTOS));
    }

    @Test
    public void comparacionGrandes() {

        Assert.assertEquals(8811, puntuadorGrandes.calcularPuntuacion(jugada1));
        Assert.assertEquals(9991, puntuadorGrandes.calcularPuntuacion(jugada2));
        Assert.assertEquals(7776, puntuadorGrandes.calcularPuntuacion(jugada3));
        Assert.assertEquals(8811, puntuadorGrandes.calcularPuntuacion(jugada4));
        Assert.assertEquals(8661, puntuadorGrandes.calcularPuntuacion(jugada5));
        Assert.assertEquals(6543, puntuadorGrandes.calcularPuntuacion(jugada6));
        Assert.assertEquals(9999, puntuadorGrandes.calcularPuntuacion(jugada7));
    }
    
    @Test
    public void comparacionChicas() {

        Assert.assertEquals(22, puntuadorChicas.calcularPuntuacion(jugada1));
        Assert.assertEquals(42, puntuadorChicas.calcularPuntuacion(jugada2));
        Assert.assertEquals(37, puntuadorChicas.calcularPuntuacion(jugada3));
        Assert.assertEquals(-8811, puntuadorChicas.calcularPuntuacion(jugada4));
        Assert.assertEquals(-8661, puntuadorChicas.calcularPuntuacion(jugada5));
        Assert.assertEquals(-6543, puntuadorChicas.calcularPuntuacion(jugada6));
        Assert.assertEquals(-9999, puntuadorChicas.calcularPuntuacion(jugada7));
    }
    
    @Test
    public void comparacionPares() {

        Assert.assertEquals(48811, puntuadorPares.calcularPuntuacion(jugada1));
        Assert.assertEquals(39991, puntuadorPares.calcularPuntuacion(jugada2));
        Assert.assertEquals(37776, puntuadorPares.calcularPuntuacion(jugada3));
        Assert.assertEquals(48811, puntuadorPares.calcularPuntuacion(jugada4));
        Assert.assertEquals(28661, puntuadorPares.calcularPuntuacion(jugada5));
        Assert.assertEquals(6543, puntuadorPares.calcularPuntuacion(jugada6));
        Assert.assertEquals(49999, puntuadorPares.calcularPuntuacion(jugada7));

    }
    
    @Test
    public void comparacionJuego() {

        Assert.assertEquals(22, puntuadorJuego.calcularPuntuacion(jugada1));
        Assert.assertEquals(42, puntuadorJuego.calcularPuntuacion(jugada2));
        Assert.assertEquals(37, puntuadorJuego.calcularPuntuacion(jugada3));
        Assert.assertEquals(22, puntuadorJuego.calcularPuntuacion(jugada4));
        Assert.assertEquals(25, puntuadorJuego.calcularPuntuacion(jugada5));
        Assert.assertEquals(22, puntuadorJuego.calcularPuntuacion(jugada6));
        Assert.assertEquals(40, puntuadorJuego.calcularPuntuacion(jugada7));

    }

}
