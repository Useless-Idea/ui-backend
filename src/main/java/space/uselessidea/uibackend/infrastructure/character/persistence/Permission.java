package space.uselessidea.uibackend.infrastructure.character.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
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
@Table(name = "permission")
public class Permission {

  @Id
  @Column(name = "uuid", nullable = false)
  @GeneratedValue
  private UUID uuid;

  @NotNull
  @Column(name = "name", nullable = false, length = Integer.MAX_VALUE)
  private String name;

  @NotNull
  @Column(name = "code", nullable = false, length = Integer.MAX_VALUE)
  private String code;

  @Column(name = "description", length = Integer.MAX_VALUE)
  private String description;

  @ManyToMany
  @JoinTable(name = "permission_role",
      joinColumns = @JoinColumn(name = "permission_uuid"),
      inverseJoinColumns = @JoinColumn(name = "role_uuid"))
  private Set<Role> roles = new LinkedHashSet<>();

}