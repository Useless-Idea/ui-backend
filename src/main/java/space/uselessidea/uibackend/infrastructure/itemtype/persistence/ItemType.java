package space.uselessidea.uibackend.infrastructure.itemtype.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "item_type")
public class ItemType {

  @Id
  @Column(name = "id", nullable = false)
  private Long id;

  @Column(name = "name", length = Integer.MAX_VALUE)
  private String name;

}
