package mg.razherana.notes.services;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import mg.razherana.notes.api.ApiException;
import mg.razherana.notes.dto.OptionMoyenneDto;
import mg.razherana.notes.dto.OptionStudentMoyenneDto;
import mg.razherana.notes.dto.StudentOptionDetailDto;
import mg.razherana.notes.entities.AnneeScolaire;
import mg.razherana.notes.entities.Etudiant;
import mg.razherana.notes.entities.GroupeMatiere;
import mg.razherana.notes.entities.Inscription;
import mg.razherana.notes.entities.Notes;
import mg.razherana.notes.entities.Option;
import mg.razherana.notes.entities.OptionUniteEnseignement;
import mg.razherana.notes.entities.Semestre;
import mg.razherana.notes.entities.Session;
import mg.razherana.notes.entities.UniteEnseignement;
import mg.razherana.notes.repositories.EtudiantRepository;
import mg.razherana.notes.repositories.InscriptionRepository;
import mg.razherana.notes.repositories.NotesRepository;
import mg.razherana.notes.repositories.OptionRepository;
import mg.razherana.notes.repositories.SemestreRepository;
import mg.razherana.notes.repositories.SessionRepository;
import mg.razherana.notes.repositories.UniteEnseignementRepository;

@Service
@RequiredArgsConstructor
public class NotesService {

  private static final int NOTE_SCALE = 2;
  private static final BigDecimal ZERO_NOTE = BigDecimal.ZERO.setScale(NOTE_SCALE, RoundingMode.HALF_UP);
  private static final BigDecimal MIN_NOTE = ZERO_NOTE;
  private static final BigDecimal MAX_NOTE = BigDecimal.valueOf(20).setScale(NOTE_SCALE, RoundingMode.HALF_UP);

  private final NotesRepository notesRepository;
  private final InscriptionRepository inscriptionRepository;
  private final SessionRepository sessionRepository;
  private final UniteEnseignementRepository uniteEnseignementRepository;
  private final OptionRepository optionRepository;
  private final SemestreRepository semestreRepository;
  private final EtudiantRepository etudiantRepository;

  public List<Notes> findAll() {
    return notesRepository.findAll();
  }

  public Notes findById(Long id) {
    return notesRepository.findById(id)
        .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "NOTE_NOT_FOUND", "Note " + id + " not found", null));
  }

  public Notes create(Notes note) {
    BigDecimal valeur = normalizeAndValidateValeur(note.getValeur());
    Notes toSave = new Notes();
    toSave.setValeur(valeur);
    toSave.setInscription(resolveInscription(note.getInscription()));
    toSave.setSession(resolveSession(note.getSession()));
    toSave.setUnite(resolveUnite(note.getUnite()));
    return notesRepository.save(toSave);
  }

  public Notes update(Long id, Notes note) {
    Notes existing = findById(id);
    BigDecimal valeur = normalizeAndValidateValeur(note.getValeur());
    existing.setValeur(valeur);
    existing.setInscription(resolveInscription(note.getInscription()));
    existing.setSession(resolveSession(note.getSession()));
    existing.setUnite(resolveUnite(note.getUnite()));
    return notesRepository.save(existing);
  }

  public void delete(Long id) {
    Notes existing = findById(id);
    notesRepository.delete(existing);
  }

  public List<Notes> findBySemestre(Long semestreId) {
    resolveSemestre(semestreId);
    return notesRepository.findByInscriptionSemestreId(semestreId);
  }

  public List<Notes> findBySemestreAndEtudiant(Long semestreId, Long etudiantId) {
    resolveSemestre(semestreId);
    resolveEtudiant(etudiantId);
    return notesRepository.findByInscriptionSemestreIdAndInscriptionEtudiantId(semestreId, etudiantId);
  }

  public List<Notes> findBySemestreAnnee(String annee) {
    validateAnnee(annee);
    return notesRepository.findByInscriptionSemestreAnnee(annee);
  }

  public List<OptionMoyenneDto> calculateOptionMoyennes(Long optionId, Long semestreId, Long anneeScolaireId) {
    if (optionId != null) {
      ensureOptionExists(optionId);
    }
    if (semestreId != null) {
      resolveSemestre(semestreId);
    }

    List<Option> options = optionRepository.findAllWithAssociations(optionId, semestreId);
    if (options.isEmpty()) {
      return List.of();
    }

    Set<Long> semestreIds = options.stream()
        .filter(option -> option.getSemestre() != null && option.getSemestre().getId() != null)
        .map(option -> option.getSemestre().getId())
        .collect(Collectors.toSet());

    Map<Long, List<Inscription>> inscriptionsBySemestre = semestreIds.isEmpty()
        ? Map.of()
        : inscriptionRepository.findBySemestreIdsWithNotes(semestreIds, anneeScolaireId).stream()
            .collect(Collectors.groupingBy(inscription -> inscription.getSemestre().getId()));

    List<OptionMoyenneDto> results = new ArrayList<>();
    for (Option option : options) {
      Long currentSemestreId = option.getSemestre() != null ? option.getSemestre().getId() : null;
      List<Inscription> inscriptions = currentSemestreId != null
          ? inscriptionsBySemestre.getOrDefault(currentSemestreId, List.of())
          : List.of();
      results.add(buildOptionMoyenne(option, inscriptions));
    }
    return results;
  }

  public List<StudentOptionDetailDto> fetchStudentOptionsDetails(Long semestreId, Long etudiantId) {
    resolveSemestre(semestreId);
    resolveEtudiant(etudiantId);

    // Get the inscription for this student and semester
    List<Notes> notesList = notesRepository.findByInscriptionSemestreIdAndInscriptionEtudiantId(semestreId, etudiantId);
    if (notesList.isEmpty()) {
      return List.of();
    }

    // Group notes by option
    Map<Long, StudentOptionDetailDto> optionMap = new LinkedHashMap<>();
    Map<Long, Map<Long, StudentOptionDetailDto.UniteDetailDto>> unitesByOption = new LinkedHashMap<>();

    for (Notes note : notesList) {
      if (note.getUnite() == null || note.getInscription() == null || note.getSession() == null) {
        continue;
      }

      // Find the option associated with this unite
      Option option = findOptionForUnite(note.getUnite().getId(), semestreId);
      if (option == null) {
        continue;
      }

      Long optionId = option.getId();
      if (!optionMap.containsKey(optionId)) {
        optionMap.put(optionId, new StudentOptionDetailDto(
            optionId,
            option.getLibelle(),
            new ArrayList<>()
        ));
        unitesByOption.put(optionId, new LinkedHashMap<>());
      }

      // Find the OptionUniteEnseignement for credits and group info
      OptionUniteEnseignement optionUnite = findOptionUniteEnseignement(optionId, note.getUnite().getId());
      int credits = optionUnite != null && optionUnite.getCredits() != null ? optionUnite.getCredits() : 0;
      String groupeLibelle = optionUnite != null && optionUnite.getGroupe() != null
          ? optionUnite.getGroupe().getLibelle()
          : "";

      StudentOptionDetailDto.UniteDetailDto uniteDetail = new StudentOptionDetailDto.UniteDetailDto(
          note.getUnite().getId(),
          note.getUnite().getCodeMatiere(),
          note.getUnite().getIntitule(),
          note.getValeur(),
          note.getSession().getLibelle(),
          credits,
          groupeLibelle
      );

      // Store by unite ID to avoid duplicates (keep the best note)
      Map<Long, StudentOptionDetailDto.UniteDetailDto> unitesMap = unitesByOption.get(optionId);
      if (!unitesMap.containsKey(note.getUnite().getId())) {
        unitesMap.put(note.getUnite().getId(), uniteDetail);
      } else {
        // Keep the highest note for this unite
        StudentOptionDetailDto.UniteDetailDto existing = unitesMap.get(note.getUnite().getId());
        if (uniteDetail.note().compareTo(existing.note()) > 0) {
          unitesMap.put(note.getUnite().getId(), uniteDetail);
        }
      }
    }

    // Build the result
    for (Map.Entry<Long, StudentOptionDetailDto> entry : optionMap.entrySet()) {
      List<StudentOptionDetailDto.UniteDetailDto> unites = new ArrayList<>(unitesByOption.get(entry.getKey()).values());
      StudentOptionDetailDto dto = new StudentOptionDetailDto(
          entry.getValue().optionId(),
          entry.getValue().optionLibelle(),
          unites
      );
      optionMap.put(entry.getKey(), dto);
    }

    return new ArrayList<>(optionMap.values());
  }

  private Option findOptionForUnite(Long uniteId, Long semestreId) {
    List<Option> options = optionRepository.findBySemestreId(semestreId);
    for (Option option : options) {
      if (option.getOptionUnites() != null) {
        for (OptionUniteEnseignement optionUnite : option.getOptionUnites()) {
          if (optionUnite.getUnite() != null && optionUnite.getUnite().getId().equals(uniteId)) {
            return option;
          }
        }
      }
    }
    return null;
  }

  private OptionUniteEnseignement findOptionUniteEnseignement(Long optionId, Long uniteId) {
    Option option = optionRepository.findById(optionId).orElse(null);
    if (option == null || option.getOptionUnites() == null) {
      return null;
    }
    return option.getOptionUnites().stream()
        .filter(oue -> oue.getUnite() != null && oue.getUnite().getId().equals(uniteId))
        .findFirst()
        .orElse(null);
  }

  private OptionMoyenneDto buildOptionMoyenne(Option option, List<Inscription> inscriptions) {
    List<OptionUniteEnseignement> optionUnites = option.getOptionUnites() != null
        ? option.getOptionUnites()
        : List.of();

    Map<Long, List<OptionUniteEnseignement>> unitesByGroup = optionUnites.stream()
        .filter(oue -> oue.getUnite() != null && oue.getUnite().getId() != null)
        .collect(Collectors.groupingBy(
            oue -> oue.getGroupe() != null && oue.getGroupe().getId() != null ? oue.getGroupe().getId() : -1L,
            LinkedHashMap::new,
            Collectors.toList()));

    Map<Long, GroupeMatiere> groupeById = optionUnites.stream()
        .filter(oue -> oue.getGroupe() != null && oue.getGroupe().getId() != null)
        .collect(Collectors.toMap(oue -> oue.getGroupe().getId(), OptionUniteEnseignement::getGroupe,
            (left, right) -> left));

    List<OptionStudentMoyenneDto> etudiants = new ArrayList<>();
    for (Inscription inscription : inscriptions) {
      etudiants.add(computeStudentAverage(unitesByGroup, groupeById, inscription));
    }
    etudiants.sort((a, b) -> {
      BigDecimal moyenneB = b.moyenne() != null ? b.moyenne() : ZERO_NOTE;
      BigDecimal moyenneA = a.moyenne() != null ? a.moyenne() : ZERO_NOTE;
      return moyenneB.compareTo(moyenneA);
    });

    Long semestreId = option.getSemestre() != null ? option.getSemestre().getId() : null;
    String semestreLibelle = option.getSemestre() != null ? option.getSemestre().getLibelle() : null;

    return new OptionMoyenneDto(option.getId(), option.getLibelle(), semestreId, semestreLibelle, etudiants);
  }

  private OptionStudentMoyenneDto computeStudentAverage(
      Map<Long, List<OptionUniteEnseignement>> unitesByGroup,
      Map<Long, GroupeMatiere> groupeById,
      Inscription inscription) {

  Map<Long, BigDecimal> highestNotesByUnite = buildHighestNotesByUnite(inscription);
  BigDecimal weightedSum = ZERO_NOTE;
    int totalCredits = 0;

    for (Map.Entry<Long, List<OptionUniteEnseignement>> entry : unitesByGroup.entrySet()) {
      Long groupId = entry.getKey();
      List<OptionUniteEnseignement> unites = entry.getValue();
      boolean obligatoire = isObligatoire(groupeById.get(groupId));

      if (obligatoire) {
        for (OptionUniteEnseignement association : unites) {
          int credits = safeCredits(association.getCredits());
          if (credits <= 0) {
            continue;
          }
      BigDecimal note = highestNotesByUnite
        .getOrDefault(association.getUnite().getId(), ZERO_NOTE);
          weightedSum = weightedSum.add(note.multiply(BigDecimal.valueOf(credits)));
          totalCredits += credits;
        }
      } else {
        OptionUniteEnseignement bestAssociation = null;
        BigDecimal bestNote = null;
        int bestCredits = 0;

        for (OptionUniteEnseignement association : unites) {
          int credits = safeCredits(association.getCredits());
          if (credits <= 0) {
            continue;
          }
      BigDecimal note = highestNotesByUnite
        .getOrDefault(association.getUnite().getId(), ZERO_NOTE);
          if (bestAssociation == null || note.compareTo(bestNote) > 0
              || (note.compareTo(bestNote) == 0 && credits > bestCredits)) {
            bestAssociation = association;
            bestNote = note;
            bestCredits = credits;
          }
        }

        if (bestAssociation != null && bestNote != null) {
          weightedSum = weightedSum.add(bestNote.multiply(BigDecimal.valueOf(bestCredits)));
          totalCredits += bestCredits;
        }
      }
    }

  BigDecimal moyenne = totalCredits == 0
    ? ZERO_NOTE
    : weightedSum.divide(BigDecimal.valueOf(totalCredits), NOTE_SCALE, RoundingMode.HALF_UP);

    Etudiant etudiant = inscription.getEtudiant();
    return new OptionStudentMoyenneDto(
        inscription.getId(),
        etudiant != null ? etudiant.getId() : null,
        etudiant != null ? etudiant.getNom() : null,
        etudiant != null ? etudiant.getPrenom() : null,
        formatAnneeScolaire(inscription.getAnneeScolaire()),
        moyenne.setScale(NOTE_SCALE, RoundingMode.HALF_UP),
        totalCredits);
  }

  private Map<Long, BigDecimal> buildHighestNotesByUnite(Inscription inscription) {
    Map<Long, BigDecimal> highest = new HashMap<>();
    if (inscription.getNotes() == null) {
      return highest;
    }
    for (Notes note : inscription.getNotes()) {
      if (note == null || note.getUnite() == null || note.getUnite().getId() == null || note.getValeur() == null) {
        continue;
      }
      BigDecimal valeur = normalizeValeur(note.getValeur());
      highest.merge(note.getUnite().getId(), valeur, (existing, incoming) -> existing.max(incoming));
    }
    return highest;
  }

  private boolean isObligatoire(GroupeMatiere groupe) {
    if (groupe == null || groupe.getLibelle() == null) {
      return false;
    }
    String libelle = groupe.getLibelle().trim().toLowerCase(Locale.ROOT);
    return libelle.contains("obligatoire");
  }

  private int safeCredits(Integer credits) {
    return credits != null ? credits : 0;
  }

  private String formatAnneeScolaire(AnneeScolaire anneeScolaire) {
    if (anneeScolaire == null) {
      return null;
    }
    LocalDate start = anneeScolaire.getAnnee1();
    LocalDate end = anneeScolaire.getAnnee2();
    if (start == null && end == null) {
      return null;
    }
    int startYear = start != null ? start.getYear() : (end != null ? end.minusYears(1).getYear() : 0);
    int endYear = end != null ? end.getYear() : (start != null ? start.plusYears(1).getYear() : startYear);
    if (startYear == 0 && endYear == 0) {
      return null;
    }
    return startYear + " - " + endYear;
  }

  private void ensureOptionExists(Long optionId) {
    optionRepository.findById(optionId)
        .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "OPTION_NOT_FOUND",
            "Option " + optionId + " not found", optionId));
  }

  private BigDecimal normalizeAndValidateValeur(BigDecimal valeur) {
    if (valeur == null) {
      throw new ApiException(HttpStatus.BAD_REQUEST, "INVALID_NOTE_VALUE", "valeur must be between 0 and 20",
          null);
    }
    BigDecimal normalized = normalizeValeur(valeur);
    if (normalized.compareTo(MIN_NOTE) < 0 || normalized.compareTo(MAX_NOTE) > 0) {
      throw new ApiException(HttpStatus.BAD_REQUEST, "INVALID_NOTE_VALUE", "valeur must be between 0 and 20",
          normalized);
    }
    return normalized;
  }

  private BigDecimal normalizeValeur(BigDecimal valeur) {
    return valeur.setScale(NOTE_SCALE, RoundingMode.HALF_UP);
  }

  private Inscription resolveInscription(Inscription inscription) {
    if (inscription == null || inscription.getId() == null) {
      throw new ApiException(HttpStatus.BAD_REQUEST, "INSCRIPTION_REQUIRED", "inscription is required", null);
    }
    return inscriptionRepository.findById(inscription.getId())
        .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "INSCRIPTION_NOT_FOUND",
            "Inscription " + inscription.getId() + " not found", inscription.getId()));
  }

  private Session resolveSession(Session session) {
    if (session == null || session.getId() == null) {
      throw new ApiException(HttpStatus.BAD_REQUEST, "SESSION_REQUIRED", "session is required", null);
    }
    Session found = sessionRepository.findById(session.getId())
        .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "SESSION_NOT_FOUND",
            "Session " + session.getId() + " not found", session.getId()));
    if (found.getLibelle() == null || found.getLibelle().isBlank()) {
      throw new ApiException(HttpStatus.BAD_REQUEST, "INVALID_SESSION_LIBELLE",
          "session libelle must describe the exam (e.g. 'Rattrapage', 'Examen normal')", found.getId());
    }
    return found;
  }

  private UniteEnseignement resolveUnite(UniteEnseignement unite) {
    if (unite == null || unite.getId() == null) {
      throw new ApiException(HttpStatus.BAD_REQUEST, "UNITE_REQUIRED", "unite is required", null);
    }
    return uniteEnseignementRepository.findById(unite.getId())
        .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "UNITE_NOT_FOUND",
            "Unite d'enseignement " + unite.getId() + " not found", unite.getId()));
  }

  private Semestre resolveSemestre(Long semestreId) {
    if (semestreId == null) {
      throw new ApiException(HttpStatus.BAD_REQUEST, "SEMESTRE_REQUIRED", "semestre is required", null);
    }
    return semestreRepository.findById(semestreId)
        .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "SEMESTRE_NOT_FOUND",
            "Semestre " + semestreId + " not found", semestreId));
  }

  private Etudiant resolveEtudiant(Long etudiantId) {
    if (etudiantId == null) {
      throw new ApiException(HttpStatus.BAD_REQUEST, "ETUDIANT_REQUIRED", "etudiant is required", null);
    }
    return etudiantRepository.findById(etudiantId)
        .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "ETUDIANT_NOT_FOUND",
            "Etudiant " + etudiantId + " not found", etudiantId));
  }

  private void validateAnnee(String annee) {
    if (annee == null || annee.isBlank()) {
      throw new ApiException(HttpStatus.BAD_REQUEST, "SEMESTRE_ANNEE_REQUIRED", "annee is required", annee);
    }
  }
}
