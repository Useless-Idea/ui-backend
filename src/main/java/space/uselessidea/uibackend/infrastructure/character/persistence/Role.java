package space.uselessidea.uibackend.infrastructure.character.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "role")
public class Role {

  @Id
  @Column(name = "uuid", nullable = false)
  @GeneratedValue
  private UUID id;

  @NotNull
  @Column(name = "name", nullable = false, length = Integer.MAX_VALUE)
  private String name;

  @NotNull
  @Column(name = "code", nullable = false, length = Integer.MAX_VALUE)
  private String code;

  @Column(name = "description", length = Integer.MAX_VALUE)
  private String description;

  @ManyToMany(mappedBy = "roles")
  private Set<Permission> permissions = new LinkedHashSet<>();

}