package space.uselessidea.uibackend.infrastructure.character.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import java.util.LinkedHashSet;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "\"character\"")
public class Character {

  @Id
  @Column(name = "id", nullable = false)
  private Long id;

  @NotNull
  @Column(name = "name", nullable = false, length = Integer.MAX_VALUE)
  private String name;

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "corporation_id", nullable = false)
  private Corporation corporation;

  @NotNull
  @Column(name = "is_token_active", nullable = false)
  private Boolean isTokenActive = false;

  @NotNull
  @Column(name = "is_block", nullable = false)
  private Boolean isBlock = false;

  @ManyToMany
  @JoinTable(name = "character_role",
      joinColumns = @JoinColumn(name = "character_id"),
      inverseJoinColumns = @JoinColumn(name = "role_uuid"))
  private Set<Role> roles = new LinkedHashSet<>();

}