package space.uselessidea.uibackend.infrastructure.term.persistence;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "\"terms\"")
public class Term {

  @Id
  @Column(name = "id", unique = true, nullable = false)
  private UUID id;

  @NotNull
  @Column(name = "term", nullable = false)
  private String term;

  @NotNull
  @Column(name = "description", nullable = false)
  private String description;
}

