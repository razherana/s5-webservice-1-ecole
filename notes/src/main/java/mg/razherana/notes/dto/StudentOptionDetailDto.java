package mg.razherana.notes.dto;

import java.math.BigDecimal;
import java.util.List;

public record StudentOptionDetailDto(
    Long optionId,
    String optionLibelle,
    List<UniteDetailDto> unites
) {

    public record UniteDetailDto(
        Long uniteId,
        String codeMatiere,
        String intitule,
        BigDecimal note,
        String sessionLibelle,
        Integer credits,
        String groupeLibelle
    ) {
    }
}
