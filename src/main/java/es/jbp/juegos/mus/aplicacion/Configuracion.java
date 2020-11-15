package es.jbp.juegos.mus.aplicacion;

import es.jbp.juegos.mus.servicios.ServicioJugadores;
import java.nio.charset.StandardCharsets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 *
 * @author jorge
 */
@Configuration
public class Configuracion {

    @Autowired
    private ServicioJugadores servicioJugadores;
    
    
    @Bean
    public LettuceConnectionFactory redisConnectionFactory() {
        return new LettuceConnectionFactory(new RedisStandaloneConfiguration("core.jorgeberjano.es", 6379));
    }
    
//    @Bean
//    public JedisConnectionFactory redisConnectionFactory() {
//        RedisStandaloneConfiguration config = new RedisStandaloneConfiguration("core.jorgeberjano.es", 6379);
//        return new JedisConnectionFactory(config);
//    }
    
    @Bean 
    public GenericJackson2JsonRedisSerializer jsonRedisSerializer() {
        return new GenericJackson2JsonRedisSerializer();
    }

    @Bean
    public RedisTemplate<?, ?> redisTemplate() {
        RedisTemplate<?, ?> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory());
        template.setDefaultSerializer(jsonRedisSerializer());
        template.setKeySerializer(new StringRedisSerializer(StandardCharsets.UTF_8));
        template.setHashKeySerializer(jsonRedisSerializer());
        template.setValueSerializer(jsonRedisSerializer());
        return template;
    }

    
    @Bean
    public RedisMessageListenerContainer redisContainer() {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(redisConnectionFactory());
        container.addMessageListener(servicioJugadores, new ChannelTopic("mensajes"));
        return container;
    }

}
