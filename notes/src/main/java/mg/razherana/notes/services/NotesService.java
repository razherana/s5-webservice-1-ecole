package mg.razherana.notes.services;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import mg.razherana.notes.api.ApiException;
import mg.razherana.notes.entities.Inscription;
import mg.razherana.notes.entities.Notes;
import mg.razherana.notes.entities.Session;
import mg.razherana.notes.entities.UniteEnseignement;
import mg.razherana.notes.repositories.InscriptionRepository;
import mg.razherana.notes.repositories.NotesRepository;
import mg.razherana.notes.repositories.SessionRepository;
import mg.razherana.notes.repositories.UniteEnseignementRepository;

@Service
@RequiredArgsConstructor
public class NotesService {

  private final NotesRepository notesRepository;
  private final InscriptionRepository inscriptionRepository;
  private final SessionRepository sessionRepository;
  private final UniteEnseignementRepository uniteEnseignementRepository;

  public List<Notes> findAll() {
    return notesRepository.findAll();
  }

  public Notes findById(Long id) {
    return notesRepository.findById(id)
        .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "NOTE_NOT_FOUND", "Note " + id + " not found", null));
  }

  public Notes create(Notes note) {
    validateValeur(note.getValeur());
    Notes toSave = new Notes();
    toSave.setValeur(note.getValeur());
    toSave.setInscription(resolveInscription(note.getInscription()));
    toSave.setSession(resolveSession(note.getSession()));
    toSave.setUnite(resolveUnite(note.getUnite()));
    return notesRepository.save(toSave);
  }

  public Notes update(Long id, Notes note) {
    Notes existing = findById(id);
    validateValeur(note.getValeur());
    existing.setValeur(note.getValeur());
    existing.setInscription(resolveInscription(note.getInscription()));
    existing.setSession(resolveSession(note.getSession()));
    existing.setUnite(resolveUnite(note.getUnite()));
    return notesRepository.save(existing);
  }

  public void delete(Long id) {
    Notes existing = findById(id);
    notesRepository.delete(existing);
  }

  private void validateValeur(Integer valeur) {
    if (valeur == null || valeur < 0 || valeur > 20) {
      throw new ApiException(HttpStatus.BAD_REQUEST, "INVALID_NOTE_VALUE", "valeur must be between 0 and 20", valeur);
    }
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
}
