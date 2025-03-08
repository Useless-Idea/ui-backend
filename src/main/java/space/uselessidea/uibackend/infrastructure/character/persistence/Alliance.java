package space.uselessidea.uibackend.infrastructure.character.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import java.util.LinkedHashSet;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "alliance")
public class Alliance {

  @Id
  @Column(name = "id", nullable = false)
  private Long id;

  @Column(name = "name", length = Integer.MAX_VALUE)
  private String name;

  @NotNull
  @Column(name = "is_blocked", nullable = false)
  private Boolean isBlocked = false;

  @OneToMany(mappedBy = "alliance")
  private Set<Corporation> corporations = new LinkedHashSet<>();

}