package mg.razherana.notes.dto;

import java.util.List;

public record OptionMoyenneDto(
    Long optionId,
    String optionLibelle,
    Long semestreId,
    String semestreLibelle,
    List<OptionStudentMoyenneDto> etudiants
) {
}
