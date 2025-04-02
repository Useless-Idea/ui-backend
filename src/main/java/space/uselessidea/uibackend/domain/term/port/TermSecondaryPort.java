package space.uselessidea.uibackend.domain.term.port;

import java.util.Optional;
import space.uselessidea.uibackend.domain.term.dto.TermData;

public interface TermSecondaryPort {

  TermData saveTerm(TermData termData);

  Optional<TermData> getTermData(TermData termData);

  void delete(String term);
}
