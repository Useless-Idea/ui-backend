package space.uselessidea.uibackend.infrastructure.paxdei.persistence;

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
@Table(name = "point")
public class PointEntity {

  @Id
  @Column(name = "uuid", nullable = false)
  @GeneratedValue
  private UUID uuid;

  @NotNull
  private Long x;
  @NotNull
  private Long y;

  @NotNull
  private String text;

}
