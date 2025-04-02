package space.uselessidea.uibackend.infrastructure.term.adapter;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import space.uselessidea.uibackend.domain.exception.ApplicationException;
import space.uselessidea.uibackend.domain.exception.ErrorCode;
import space.uselessidea.uibackend.domain.term.dto.TermData;
import space.uselessidea.uibackend.domain.term.port.TermSecondaryPort;
import space.uselessidea.uibackend.infrastructure.term.persistence.Term;
import space.uselessidea.uibackend.infrastructure.term.repository.TermRepository;

@Service
@RequiredArgsConstructor
public class TermAdapter implements TermSecondaryPort {

  private final TermRepository termRepository;

  public Term saveTermData(TermData termData) {
    // validate termData

  }

  public List<Term> findAllTerms() {
    List<Term> listOfTerms = termRepository.findAll();
    return listOfTerms;
  }

  public String deleteAllTerms() {
    termRepository.deleteAll();
    return """
        Repo wyczyszczone
        """;
  }

  public String modifyTermDesc(TermData termData) {
    Term term = findTermByTerm(termData.getTerm());
    if (term == null) {
      String msg = String.format("Term % nie istnieje.", termData.getTerm());
      return msg;
    } else {

      term.setDescription(termData.getDescription());
      termRepository.save(term);

      String msg = String.format("Term %s zostal zmodyfikowany.", term.getTerm());
      return msg;
    }
  }


  @Override
  public TermData saveTerm(TermData termData) {

    Term term = new Term();
    term.setTerm(termData.getTerm());
    term.setDescription(termData.getDescription());
    term = termRepository.save(term);
    return map(term);
  }

  @Override
  public Optional<TermData> getTermData(TermData termData) {
    return termRepository.findByTerm(termData.getTerm()).map(this::map);
  }

  @Override
  public void delete(String term) {
    Term expectedTerm = termRepository.findByTerm(term.toLowerCase())
        .orElseThrow(() -> new ApplicationException(ErrorCode.TERM_NOT_EXIST));
    termRepository.delete(expectedTerm);
  }

  private TermData map(Term term) {
    return TermData.builder()
        .description(term.getDescription())
        .term(term.getTerm())
        .build();
  }
}
