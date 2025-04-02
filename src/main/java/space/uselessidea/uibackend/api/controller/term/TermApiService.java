package space.uselessidea.uibackend.api.controller.term;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import space.uselessidea.uibackend.domain.term.dto.TermData;
import space.uselessidea.uibackend.domain.term.port.TermPrimaryPort;

@Service
@RequiredArgsConstructor
public class TermApiService {

  private final TermPrimaryPort termPrimaryPort;

  public void saveTermData(TermData entity) {
    termPrimaryPort.saveTermData(entity);
  }

  public TermData getTermData(String term) {
    TermData termData = TermData.builder()
        .term(term)
        .build();
    return termPrimaryPort.getByTermData(termData);
  }

  public void deleteTerm(String term) {
    termPrimaryPort.deleteTerm(term);
  }
}
