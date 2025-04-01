package space.uselessidea.uibackend.infrastructure.term.adapter;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import space.uselessidea.uibackend.domain.term.dto.TermData;
import space.uselessidea.uibackend.infrastructure.term.persistence.Term;
import space.uselessidea.uibackend.infrastructure.term.repository.TermRepository;

@Service
@RequiredArgsConstructor
public class TermAdapter {
    
    private final TermRepository termRepository;

    public Term saveTermData(TermData termData){
        // validate termData

        Term term = new Term();
        term.setId(UUID.randomUUID());
        term.setTerm(termData.getTerm());
        term.setDescription(termData.getDescription());
        term = termRepository.save(term);
        return term;
    }
    public Term findTermByTerm(String term){
        String termString = new String(term);
        Term expectedTerm = termRepository.findByTerm(termString);
        return expectedTerm;
    }
    public List<Term> findAllTerms(){
        List<Term> listOfTerms = termRepository.findAll();
        return listOfTerms;
    }
    public String deleteAllTerms(){
        termRepository.deleteAll();
        return """
                Repo wyczyszczone
                """;
    }
    public String modifyTermDesc(TermData termData){
        Term term = findTermByTerm(termData.getTerm());
        if(term == null){
            String msg = String.format("Term % nie istnieje.", termData.getTerm());
            return msg;
        } else {
            
            term.setDescription(termData.getDescription());
            termRepository.save(term);
            
            String msg = String.format("Term %s zostal zmodyfikowany.", term.getTerm());
            return msg;
        }
    }
    public String deleteTerm(String term){
        Term expectedTerm = findTermByTerm(term.toLowerCase());
        if(expectedTerm == null){
            String msg = String.format("Term %s nie istnieje", term);
            return msg;
        } else {
            termRepository.delete(expectedTerm);
            String msg = String.format("Term %s usuniety", term);
            return msg;
        }
    }
    // Optional<Term> findByTerm(String term);
}
