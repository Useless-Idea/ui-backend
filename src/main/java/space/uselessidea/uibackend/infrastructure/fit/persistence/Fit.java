package space.uselessidea.uibackend.infrastructure.fit.persistence;

import io.hypersistence.utils.hibernate.type.json.JsonBinaryType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.Type;
import org.hibernate.type.SqlTypes;


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
  private String shipName;

  @NotNull
  private String fitName;

  @NotNull
  @Column(name = "eft", nullable = false, length = Integer.MAX_VALUE)
  private String eft;

  @Type(JsonBinaryType.class)
  @JdbcTypeCode(SqlTypes.JSON)
  @Column(name = "pilots", columnDefinition = "jsonb")
  private Pilots pilots;

}
