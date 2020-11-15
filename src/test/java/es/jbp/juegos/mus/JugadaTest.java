package es.jbp.juegos.mus;

import es.jbp.juegos.mus.persistencia.Jugada;
import es.jbp.juegos.mus.persistencia.Naipe;
import es.jbp.juegos.mus.enumerados.Palo;
import es.jbp.juegos.mus.logica.Puntuador;
import es.jbp.juegos.mus.logica.PuntuadorChicas;
import es.jbp.juegos.mus.logica.PuntuadorGrandes;
import es.jbp.juegos.mus.logica.PuntuadorJuego;
import es.jbp.juegos.mus.logica.PuntuadorPares;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author jorge
 */
public class JugadaTest {

    private static final int K = 10;
    private static final int Q = 9;
    private static final int J = 8;

    public JugadaTest() {
    }

    static Jugada jugadaQQ21 = new Jugada();
    static Jugada jugadaKK31 = new Jugada();
    static Jugada jugadaJJJ7 = new Jugada();
    static Jugada jugadaQQ11 = new Jugada();
    static Jugada jugadaQ772 = new Jugada();
    static Jugada jugada7654 = new Jugada();
    static Jugada jugadaKKK3 = new Jugada();
    static Jugada jugada3QJ2 = new Jugada();
    static Jugada jugadaKQ21 = new Jugada();

    @BeforeClass
    public static void setUpClass() {

        jugadaQQ21.agregar(new Naipe(Q, Palo.BASTOS));
        jugadaQQ21.agregar(new Naipe(Q, Palo.BASTOS));
        jugadaQQ21.agregar(new Naipe(2, Palo.BASTOS));
        jugadaQQ21.agregar(new Naipe(1, Palo.BASTOS));

        jugadaKK31.agregar(new Naipe(K, Palo.BASTOS));
        jugadaKK31.agregar(new Naipe(K, Palo.BASTOS));
        jugadaKK31.agregar(new Naipe(3, Palo.BASTOS));
        jugadaKK31.agregar(new Naipe(1, Palo.BASTOS));

        jugadaJJJ7.agregar(new Naipe(J, Palo.BASTOS));
        jugadaJJJ7.agregar(new Naipe(J, Palo.BASTOS));
        jugadaJJJ7.agregar(new Naipe(J, Palo.BASTOS));
        jugadaJJJ7.agregar(new Naipe(7, Palo.BASTOS));

        jugadaQQ11.agregar(new Naipe(Q, Palo.BASTOS));
        jugadaQQ11.agregar(new Naipe(Q, Palo.BASTOS));
        jugadaQQ11.agregar(new Naipe(1, Palo.BASTOS));
        jugadaQQ11.agregar(new Naipe(1, Palo.BASTOS));

        jugadaQ772.agregar(new Naipe(Q, Palo.BASTOS));
        jugadaQ772.agregar(new Naipe(7, Palo.BASTOS));
        jugadaQ772.agregar(new Naipe(7, Palo.BASTOS));
        jugadaQ772.agregar(new Naipe(2, Palo.BASTOS));

        jugada7654.agregar(new Naipe(7, Palo.BASTOS));
        jugada7654.agregar(new Naipe(6, Palo.BASTOS));
        jugada7654.agregar(new Naipe(5, Palo.BASTOS));
        jugada7654.agregar(new Naipe(4, Palo.BASTOS));

        jugadaKKK3.agregar(new Naipe(K, Palo.BASTOS));
        jugadaKKK3.agregar(new Naipe(K, Palo.BASTOS));
        jugadaKKK3.agregar(new Naipe(K, Palo.BASTOS));
        jugadaKKK3.agregar(new Naipe(3, Palo.BASTOS));

        jugada3QJ2.agregar(new Naipe(3, Palo.BASTOS));
        jugada3QJ2.agregar(new Naipe(Q, Palo.BASTOS));
        jugada3QJ2.agregar(new Naipe(J, Palo.BASTOS));
        jugada3QJ2.agregar(new Naipe(2, Palo.BASTOS));

        jugadaKQ21.agregar(new Naipe(K, Palo.BASTOS));
        jugadaKQ21.agregar(new Naipe(Q, Palo.BASTOS));
        jugadaKQ21.agregar(new Naipe(2, Palo.BASTOS));
        jugadaKQ21.agregar(new Naipe(1, Palo.BASTOS));

    }

    @Test
    public void comparacionGrandes() {
        Puntuador puntuador = new PuntuadorGrandes();
        Assert.assertEquals(8811, puntuador.calcularPuntuacion(jugadaQQ21));
        Assert.assertEquals(9991, puntuador.calcularPuntuacion(jugadaKK31));
        Assert.assertEquals(7776, puntuador.calcularPuntuacion(jugadaJJJ7));
        Assert.assertEquals(8811, puntuador.calcularPuntuacion(jugadaQQ11));
        Assert.assertEquals(8661, puntuador.calcularPuntuacion(jugadaQ772));
        Assert.assertEquals(6543, puntuador.calcularPuntuacion(jugada7654));
        Assert.assertEquals(9999, puntuador.calcularPuntuacion(jugadaKKK3));
        Assert.assertEquals(9871, puntuador.calcularPuntuacion(jugada3QJ2));
        Assert.assertEquals(9811, puntuador.calcularPuntuacion(jugadaKQ21));
    }

    @Test
    public void comparacionChicas() {
        Puntuador puntuador = new PuntuadorChicas();
        Assert.assertEquals(1188, puntuador.calcularPuntuacion(jugadaQQ21));
        Assert.assertEquals(1999, puntuador.calcularPuntuacion(jugadaKK31));
        Assert.assertEquals(6777, puntuador.calcularPuntuacion(jugadaJJJ7));
        Assert.assertEquals(1188, puntuador.calcularPuntuacion(jugadaQQ11));
        Assert.assertEquals(1668, puntuador.calcularPuntuacion(jugadaQ772));
        Assert.assertEquals(3456, puntuador.calcularPuntuacion(jugada7654));
        Assert.assertEquals(9999, puntuador.calcularPuntuacion(jugadaKKK3));
        Assert.assertEquals(1789, puntuador.calcularPuntuacion(jugada3QJ2));
        Assert.assertEquals(1189, puntuador.calcularPuntuacion(jugadaKQ21));
    }

    @Test
    public void comparacionPares() {
        Puntuador puntuador = new PuntuadorPares();
        Assert.assertEquals(8811, puntuador.calcularPuntuacion(jugadaQQ21));
        Assert.assertEquals(999, puntuador.calcularPuntuacion(jugadaKK31));
        Assert.assertEquals(777, puntuador.calcularPuntuacion(jugadaJJJ7));
        Assert.assertEquals(8811, puntuador.calcularPuntuacion(jugadaQQ11));
        Assert.assertEquals(66, puntuador.calcularPuntuacion(jugadaQ772));
        Assert.assertEquals(0, puntuador.calcularPuntuacion(jugada7654));
        Assert.assertEquals(9999, puntuador.calcularPuntuacion(jugadaKKK3));
        Assert.assertEquals(0, puntuador.calcularPuntuacion(jugada3QJ2));
        Assert.assertEquals(11, puntuador.calcularPuntuacion(jugadaKQ21));
    }

    @Test
    public void comparacionJuego() {
        Puntuador puntuador = new PuntuadorJuego();
        Assert.assertEquals(22, puntuador.calcularPuntuacion(jugadaQQ21));
        Assert.assertEquals(42, puntuador.calcularPuntuacion(jugadaKK31));
        Assert.assertEquals(37, puntuador.calcularPuntuacion(jugadaJJJ7));
        Assert.assertEquals(22, puntuador.calcularPuntuacion(jugadaQQ11));
        Assert.assertEquals(25, puntuador.calcularPuntuacion(jugadaQ772));
        Assert.assertEquals(22, puntuador.calcularPuntuacion(jugada7654));
        Assert.assertEquals(40, puntuador.calcularPuntuacion(jugadaKKK3));

    }

}
