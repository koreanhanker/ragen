//package example;
//
//import org.assertj.core.api.Assertions;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.redis.connection.RedisConnectionFactory;
//import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.data.redis.core.ValueOperations;
//import org.springframework.data.redis.serializer.StringRedisSerializer;
//
//@SpringBootTest
//public class RedisTest {
//
//    @Autowired
//    private RedisTemplate<String, String> redisTemplate;
//
//    @Test
//    public void testStrings() throws Exception {
//        // Given
//        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
//        String key = "type";
//
//        // When
//        valueOperations.set(key, "test");
//
//        // Then
//        String value = valueOperations.get(key);
//        System.out.println(value);
//        Assertions.assertThat(value).isEqualTo("test");
//    }
//
//    // Configuration class to provide RedisConnectionFactory and RedisTemplate beans
//    @Configuration
//    static class TestConfig {
//
//        @Bean
//        public RedisConnectionFactory redisConnectionFactory() {
//            return new LettuceConnectionFactory("mariapark.synology.me",6379); // or use your preferred Redis connection factory
//        }
//
//        @Bean
//        public RedisTemplate<String, String> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
//            RedisTemplate<String, String> redisTemplate = new RedisTemplate<>();
//            redisTemplate.setConnectionFactory(redisConnectionFactory);
//            redisTemplate.setConnectionFactory(redisConnectionFactory());
//            redisTemplate.setKeySerializer(new StringRedisSerializer());
//            redisTemplate.setValueSerializer(new StringRedisSerializer());
//            return redisTemplate;
//        }
//    }
//}
