package space.uselessidea.uibackend.domain.term;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import space.uselessidea.uibackend.domain.exception.ApplicationException;
import space.uselessidea.uibackend.domain.exception.ErrorCode;
import space.uselessidea.uibackend.domain.term.dto.TermData;
import space.uselessidea.uibackend.domain.term.port.TermPrimaryPort;
import space.uselessidea.uibackend.domain.term.port.TermSecondaryPort;

@Service
@AllArgsConstructor
public class TermService implements TermPrimaryPort {

  private final TermSecondaryPort termSecondaryPort;

  @Override
  public void saveTermData(TermData entity) {
    if (termSecondaryPort.getTermData(entity).isPresent()) {
      throw new ApplicationException(ErrorCode.TERM_ALREADY_EXIST);
    }
    termSecondaryPort.saveTerm(entity);

  }

  @Override
  public TermData getByTermData(TermData termData) {
    return termSecondaryPort.getTermData(termData).orElseThrow(() ->
        new ApplicationException(ErrorCode.TERM_NOT_EXIST));
  }

  @Override
  public void deleteTerm(String term) {
    termSecondaryPort.delete(term);

  }
}
