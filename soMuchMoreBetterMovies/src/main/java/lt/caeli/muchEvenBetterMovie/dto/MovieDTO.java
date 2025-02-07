package lt.caeli.muchEvenBetterMovie.dto;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lt.caeli.muchEvenBetterMovie.model.Screening;

import java.util.List;

public record MovieDTO(
        Long id,
        @Size(min = 3, max = 100, message = "must be between 3 and 100")
        String title,
        @Pattern(regexp = "[A-Z][A-Za-z| ]+", message = "must start with with capital letter")
        String director,
        List<Screening> screenings) {

}
