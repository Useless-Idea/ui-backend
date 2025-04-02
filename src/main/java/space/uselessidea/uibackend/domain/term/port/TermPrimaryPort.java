package space.uselessidea.uibackend.domain.term.port;

import space.uselessidea.uibackend.domain.term.dto.TermData;

public interface TermPrimaryPort {

  void saveTermData(TermData termData);

  TermData getByTermData(TermData termData);

  void deleteTerm(String term);
}
