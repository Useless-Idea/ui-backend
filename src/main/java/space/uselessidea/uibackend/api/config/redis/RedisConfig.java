package space.uselessidea.uibackend.api.config.redis;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializationContext.SerializationPair;
import space.uselessidea.uibackend.domain.fit.dto.FitDto;
import space.uselessidea.uibackend.infrastructure.eve.api.Skill;
import space.uselessidea.uibackend.infrastructure.eve.api.data.ItemTypeApiResponse;

@Configuration
public class RedisConfig {

  @Bean
  public RedisCacheConfiguration cacheConfiguration(ObjectMapper objectMapper) {
    Jackson2JsonRedisSerializer<Object> serializer = new Jackson2JsonRedisSerializer<>(Object.class);

    return RedisCacheConfiguration.defaultCacheConfig()
        .entryTtl(Duration.ofDays(60))
        .disableCachingNullValues()
        .serializeValuesWith(SerializationPair.fromSerializer(serializer));
  }

  @Bean
  public CacheManager cacheManager(ObjectMapper objectMapper, RedisConnectionFactory connectionFactory) {

    RedisCacheConfiguration itemCacheConfig = RedisCacheConfiguration.defaultCacheConfig()
        .serializeValuesWith(
            RedisSerializationContext.SerializationPair.fromSerializer(
                new Jackson2JsonRedisSerializer<>(ItemTypeApiResponse.class)
            )
        );

    RedisCacheConfiguration fitCacheConfig = RedisCacheConfiguration.defaultCacheConfig()
        .serializeValuesWith(
            RedisSerializationContext.SerializationPair.fromSerializer(
                new Jackson2JsonRedisSerializer<>(FitDto.class)
            )
        );
    JavaType type = objectMapper.getTypeFactory().constructMapType(Map.class, Long.class, Skill.class);

    RedisCacheConfiguration userSkillsCacheConfig = RedisCacheConfiguration.defaultCacheConfig()
        .entryTtl(Duration.ofMinutes(60))
        .serializeValuesWith(
            RedisSerializationContext.SerializationPair.fromSerializer(
                new Jackson2JsonRedisSerializer<>(type)
            )
        );

    Map<String, RedisCacheConfiguration> cacheConfigs = new HashMap<>();
    cacheConfigs.put("type", itemCacheConfig);
    cacheConfigs.put("fit", fitCacheConfig);
    cacheConfigs.put("user-skill", userSkillsCacheConfig);

    return RedisCacheManager.builder(connectionFactory)
        .withInitialCacheConfigurations(cacheConfigs)
        .build();
  }

}
