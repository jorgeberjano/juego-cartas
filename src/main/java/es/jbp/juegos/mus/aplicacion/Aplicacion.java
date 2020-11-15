package es.jbp.juegos.mus.aplicacion;

import es.jbp.juegos.mus.iu.Ventana;
import java.awt.EventQueue;
import javax.swing.UIManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

/**
 *
 * @author jorge
 */
@SpringBootApplication
@ComponentScan(basePackages = {"es.jbp.juegos.mus"})
@EnableRedisRepositories("es.jbp.juegos.mus.persistencia")
public class Aplicacion {

    @Autowired
    private Ventana ventana;

    public static void main(String[] args) {
        
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
        }

        ConfigurableApplicationContext ctx = new SpringApplicationBuilder(Aplicacion.class)
                .headless(false).run(args);

        EventQueue.invokeLater(() -> {
            Aplicacion app = ctx.getBean(Aplicacion.class);
            app.mostrarVentana();
        });
    }

    private void mostrarVentana() {
        ventana.inicializar();
        ventana.setVisible(true);
    }

}
