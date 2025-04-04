package space.uselessidea.uibackend.infrastructure.eve.api.token.persistence.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import space.uselessidea.uibackend.domain.FeatureEnum;

@Converter
public class FeatureConverter implements AttributeConverter<Set<FeatureEnum>, String> {

  @Override
  public String convertToDatabaseColumn(Set<FeatureEnum> featureEnums) {
    return featureEnums != null ? featureEnums.stream()
        .map(Enum::name)
        .collect(Collectors.joining(",")) : null;
  }

  @Override
  public Set<FeatureEnum> convertToEntityAttribute(String dbData) {
    return dbData != null ? Arrays.stream(dbData.split(","))
        .map(FeatureEnum::valueOf)
        .collect(Collectors.toSet()) : new HashSet<>();
  }
}
