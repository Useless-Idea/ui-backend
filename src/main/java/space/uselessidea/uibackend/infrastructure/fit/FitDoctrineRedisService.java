package space.uselessidea.uibackend.infrastructure.fit;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FitDoctrineRedisService {

  private static final String FIT_DOCTRINES_KEY = "fit:doctrines";

  private final RedisTemplate<String, String> redisTemplate;

  public void addDoctrines(Collection<String> doctrines) {
    Set<String> normalized = normalize(doctrines);
    if (normalized.isEmpty()) {
      return;
    }
    redisTemplate.opsForSet().add(FIT_DOCTRINES_KEY, normalized.toArray(String[]::new));
  }

  public List<String> getDoctrines() {
    return normalize(redisTemplate.opsForSet().members(FIT_DOCTRINES_KEY)).stream()
        .sorted(Comparator.naturalOrder())
        .toList();
  }

  public void refreshDoctrines(Collection<String> doctrines) {
    redisTemplate.delete(FIT_DOCTRINES_KEY);
    addDoctrines(doctrines);
  }

  private Set<String> normalize(Collection<String> doctrines) {
    if (doctrines == null) {
      return Set.of();
    }
    return doctrines.stream()
        .filter(doctrine -> doctrine != null && !doctrine.isBlank())
        .map(String::trim)
        .collect(Collectors.toSet());
  }
}
