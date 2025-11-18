package mg.razherana.notes.services;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import lombok.RequiredArgsConstructor;
import mg.razherana.notes.entities.Semestre;
import mg.razherana.notes.repositories.SemestreRepository;

@Service
@RequiredArgsConstructor
public class SemestreService {

  private final SemestreRepository semestreRepository;

  public List<Semestre> findAll() {
    return semestreRepository.findAll();
  }

  public Semestre findById(Long id) {
    return semestreRepository.findById(id)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Semestre not found with id: " + id));
  }

  public Semestre create(Semestre semestre) {
    return semestreRepository.save(semestre);
  }

  public Semestre update(Long id, Semestre semestre) {
    Semestre existing = findById(id);
    existing.setLibelle(semestre.getLibelle());
    existing.setAnnee(semestre.getAnnee());
    existing.setUnites(semestre.getUnites());
    existing.setInscriptions(semestre.getInscriptions());
    return semestreRepository.save(existing);
  }

  public void delete(Long id) {
    Semestre existing = findById(id);
    semestreRepository.delete(existing);
  }
}
