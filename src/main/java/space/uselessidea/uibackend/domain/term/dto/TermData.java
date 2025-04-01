package space.uselessidea.uibackend.domain.term.dto;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class TermData {


    @NotNull
    private String term;
    
    @NotNull
    private String description;
}
