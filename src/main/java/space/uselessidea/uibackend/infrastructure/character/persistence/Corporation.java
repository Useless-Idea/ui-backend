package space.uselessidea.uibackend.infrastructure.character.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "corporation")
public class Corporation {

  @Id
  @Column(name = "id", nullable = false)
  private Long id;

  @NotNull
  @Column(name = "name", nullable = false, length = Integer.MAX_VALUE)
  private String name;

  @NotNull
  @Column(name = "is_blocked", nullable = false)
  private Boolean isBlocked = true;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "alliance_id")
  private Alliance alliance;

}