package space.uselessidea.uibackend.infrastructure.eve.api.token.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.Instant;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;
import space.uselessidea.uibackend.domain.FeatureEnum;
import space.uselessidea.uibackend.infrastructure.eve.api.token.persistence.converter.FeatureConverter;

@Getter
@Setter
@Entity
@Table(name = "esi_token")
public class EsiToken {

  @Id
  @Column(name = "char_id", nullable = false)
  private Long id;

  @NotNull
  @Column(name = "exp_date", nullable = false)
  private Instant expDate;

  @Size(max = 255)
  @NotNull
  @Column(name = "refresh_token", nullable = false)
  private String refreshToken;

  @NotNull
  @Column(name = "jwt", nullable = false, length = Integer.MAX_VALUE)
  private String jwt;

  @Convert(converter = FeatureConverter.class)
  private Set<FeatureEnum> features;

}