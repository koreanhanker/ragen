package ragen.common.webSocket.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import ragen.common.webSocket.service.RedisSubscriber;

@Configuration
public class RedisConfig {

    @Value("${server.redis.host}")
    private String redisHost;

    @Value("${server.redis.port}")
    private int redisPort;

    @Value("${server.redis.channel}")
    private String redisChannel;

    @Bean
    public RedisMessageListenerContainer redisMessageListenerContainer( // (1)
                            RedisConnectionFactory connectionFactory,
                            MessageListenerAdapter listenerAdapter,
                            ChannelTopic channelTopic)
    {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.addMessageListener(listenerAdapter, channelTopic);
        return container;
    }

    @Bean
    public MessageListenerAdapter listenerAdapter(RedisSubscriber subscriber) { // (2)
        return new MessageListenerAdapter(subscriber, "onMessage");
    }

    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        return new LettuceConnectionFactory(redisHost,redisPort); // or use your preferred Redis connection factory
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory) { // (3)
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(connectionFactory);
        redisTemplate.setConnectionFactory(redisConnectionFactory());
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<>(String.class));
        return redisTemplate;
    }

    @Bean
    public ChannelTopic channelTopic() { // (4)
        return new ChannelTopic(redisChannel);
    }
}
