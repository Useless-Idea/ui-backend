package space.uselessidea.uibackend.infrastructure.term.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import space.uselessidea.uibackend.infrastructure.term.persistence.Term;

@Repository
public interface TermRepository extends JpaRepository<Term, UUID>{

    Term findByTerm(String term);
}
