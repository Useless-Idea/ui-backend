package space.uselessidea.uibackend.infrastructure.character.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import space.uselessidea.uibackend.infrastructure.character.persistence.Character;

@Repository
public interface CharacterRepository extends JpaRepository<Character, Long> {


}
