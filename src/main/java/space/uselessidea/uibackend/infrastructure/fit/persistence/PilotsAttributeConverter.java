package space.uselessidea.uibackend.infrastructure.fit.persistence;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import lombok.extern.slf4j.Slf4j;

@Converter
@Slf4j
public class PilotsAttributeConverter implements AttributeConverter<Pilots, String> {

  private static final ObjectMapper objectMapper = new ObjectMapper();

  @Override
  public String convertToDatabaseColumn(Pilots pilots) {
    try {
      return objectMapper.writeValueAsString(pilots);
    } catch (JsonProcessingException jpe) {
      log.warn("Cannot convert Pilots into JSON");
      return null;
    }
  }

  @Override
  public Pilots convertToEntityAttribute(String s) {
    try {
      return objectMapper.readValue(s, Pilots.class);
    } catch (JsonProcessingException e) {
      log.warn("Cannot convert JSON into Address");
      return null;
    }
  }
}
