package space.uselessidea.uibackend.api.controller.term;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import space.uselessidea.uibackend.domain.term.dto.TermData;
import space.uselessidea.uibackend.infrastructure.term.adapter.TermAdapter;
import space.uselessidea.uibackend.infrastructure.term.persistence.Term;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;




@RestController
@RequestMapping("/api/v1/term")
@RequiredArgsConstructor
@Slf4j
public class TermController {
    private final TermAdapter termAdapter;
    @PostMapping
    public String saveTerm(@RequestBody @Valid TermData entity){


            // dodal bym docs jakies ale nie wiem jak


        TermData termData = new TermData();
        termData.setTerm(entity.getTerm().toLowerCase());
        termData.setDescription(entity.getDescription());
        
        Term term = getTermByTerm(termData.getTerm());

        if(term == null){
            Term newTerm = termAdapter.saveTermData(termData);
            String msg = String.format("Term %s dodany.", newTerm.getTerm());
            return msg;
        } else {
            String msg = String.format("Term %s juz istnieje.", termData.getTerm());
            return msg;
        }
    }
    @GetMapping("/get")
    public Term getTermByTerm(@RequestParam String param) {
        Term term =  termAdapter.findTermByTerm(param.toLowerCase());
        return term;
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
    public String deleteTerm(@RequestParam String param){
        return termAdapter.deleteTerm(param);
    }

}
