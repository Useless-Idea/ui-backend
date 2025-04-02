package space.uselessidea.uibackend.api.controller.term;

import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import space.uselessidea.uibackend.domain.term.dto.TermData;
import space.uselessidea.uibackend.infrastructure.term.persistence.Term;


@RestController
@RequestMapping("/api/v1/term")
@RequiredArgsConstructor
@Slf4j
public class TermController {


  private final TermApiService termApiService;

  @PostMapping
  public ResponseEntity saveTerm(@RequestBody @Valid TermData entity) {

    // dodal bym docs jakies ale nie wiem jak
    termApiService.saveTermData(entity);
    return ResponseEntity.ok().build();
  }

  @GetMapping("/get")
  public ResponseEntity getTermByTerm(@RequestParam String param) {
    TermData termData = termApiService.getTermData(param);
    return ResponseEntity.ok(termData);
  }

  @GetMapping("/all")
  public List<Term> getTerms() {
    return termAdapter.findAllTerms();
  }

  @PostMapping("/mod")
  public String modTermDesc(@RequestBody @Valid TermData entity) {
    TermData termData = new TermData();
    termData.setTerm(entity.getTerm().toLowerCase());
    termData.setDescription(entity.getDescription());
    return termAdapter.modifyTermDesc(termData);
  }

  @DeleteMapping("/delete_all")
  public String deleteAll() {
    return termAdapter.deleteAllTerms();
  }

  @DeleteMapping("/delete")
  public ResponseEntity deleteTerm(@RequestParam String param) {
    termApiService.deleteTerm(param);
    return ResponseEntity.ok().build();
  }

}
