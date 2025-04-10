package space.uselessidea.uibackend.api.config.rabbit;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.DefaultClassMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {

  public static final String TOKEN_QUEUE = "q.token";
  public static final String CHAR_UPDATE_QUEUE = "q.character_update";
  public static final String ITEM_TYPE_QUEUE = "q.item_type";
  public static final String FIT_UPDATE_QUEUE = "q.fit";

  @Bean
  public MessageConverter jsonToMapMessageConverter() {
    DefaultClassMapper defaultClassMapper = new DefaultClassMapper();
    Jackson2JsonMessageConverter jackson2JsonMessageConverter = new Jackson2JsonMessageConverter();
    jackson2JsonMessageConverter.setClassMapper(defaultClassMapper);
    return jackson2JsonMessageConverter;
  }

  @Bean
  Queue tokenQueue() {
    return new Queue(TOKEN_QUEUE);
  }

  @Bean
  Queue characterUpdateQueue() {
    return new Queue(CHAR_UPDATE_QUEUE);
  }

  @Bean
  Queue itemTypeQueue() {
    return new Queue(ITEM_TYPE_QUEUE);
  }

  @Bean
  Queue fitUpdateQueue() {
    return new Queue(FIT_UPDATE_QUEUE);
  }

}
