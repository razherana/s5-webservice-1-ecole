package mg.razherana.notes.dto;

import java.math.BigDecimal;

public record OptionStudentMoyenneDto(
    Long inscriptionId,
    Long etudiantId,
    String etudiantNom,
    String etudiantPrenom,
    String anneeScolaire,
    BigDecimal moyenne,
    Integer totalCredits
) {
}
