package space.uselessidea.uibackend.domain.character.port.primary;

public interface CharacterPrimaryPort {

  void changeTokenStatus(Long charId, boolean tokenStatus);

}
