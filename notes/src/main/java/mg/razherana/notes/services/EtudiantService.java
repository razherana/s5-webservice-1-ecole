package mg.razherana.notes.services;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import mg.razherana.notes.entities.Etudiant;
import mg.razherana.notes.repositories.EtudiantRepository;
import mg.razherana.notes.api.ApiException;

@Service
@RequiredArgsConstructor
public class EtudiantService {

  private final EtudiantRepository etudiantRepository;

  public List<Etudiant> findAll() {
    return etudiantRepository.findAll();
  }

  public Etudiant findById(Long id) {
    return etudiantRepository.findById(id)
        .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "STUDENT_NOT_FOUND",
            "Etudiant " + id + " not found", null));
  }

  public Etudiant create(Etudiant etudiant) {
    return etudiantRepository.save(etudiant);
  }

  public Etudiant update(Long id, Etudiant etudiant) {
    Etudiant existing = findById(id);
    existing.setNom(etudiant.getNom());
    existing.setPrenom(etudiant.getPrenom());
    existing.setDateNaissance(etudiant.getDateNaissance());
    return etudiantRepository.save(existing);
  }

  public void delete(Long id) {
    Etudiant existing = findById(id);
    etudiantRepository.delete(existing);
  }
}
