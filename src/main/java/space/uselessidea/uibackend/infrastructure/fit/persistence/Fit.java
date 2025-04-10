package space.uselessidea.uibackend.infrastructure.fit.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "fit")
public class Fit {

  @Id
  @Column(name = "uuid", nullable = false)
  @GeneratedValue
  private UUID uuid;

  @NotNull
  @Column(name = "eft", nullable = false, length = Integer.MAX_VALUE)
  private String eft;

}
