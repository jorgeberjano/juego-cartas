package es.jbp.juegos.mus.algoritmo;

/**
 *
 * @author jorge
 */
public enum Decision {
    
    POR_SUPUESTO_QUE_SI(3),
    SEGURAMENTE_SI(2),
    PUEDE_QUE_SI(1),
    NO_SE(0),
    PUEDE_QUE_NO(-1),
    SEGURAMENTE_NO(-2),
    POR_SUPUESTO_QUE_NO(-3);
    
    private int cantidad;
    
    private Decision(int cantidad) {
        this.cantidad = cantidad;
    }

    public int getCantidad() {
        return cantidad;
    }
}
