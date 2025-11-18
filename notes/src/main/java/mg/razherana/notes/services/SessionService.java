package mg.razherana.notes.services;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import mg.razherana.notes.api.ApiException;
import mg.razherana.notes.entities.Session;
import mg.razherana.notes.repositories.SessionRepository;

@Service
@RequiredArgsConstructor
public class SessionService {

  private final SessionRepository sessionRepository;

  public List<Session> findAll() {
    return sessionRepository.findAll();
  }

  public Session findById(Long id) {
    return sessionRepository.findById(id)
        .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "SESSION_NOT_FOUND",
            "Session " + id + " not found", null));
  }

  public Session create(Session session) {
    return sessionRepository.save(session);
  }

  public Session update(Long id, Session session) {
    Session existing = findById(id);
    existing.setLibelle(session.getLibelle());
    existing.setDateSession(session.getDateSession());
    existing.setNotes(session.getNotes());
    return sessionRepository.save(existing);
  }

  public void delete(Long id) {
    Session existing = findById(id);
    sessionRepository.delete(existing);
  }
}
