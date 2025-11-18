package mg.razherana.notes.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import mg.razherana.notes.entities.Etudiant;
import mg.razherana.notes.services.EtudiantService;

@RestController
@RequestMapping("/api/etudiants")
@RequiredArgsConstructor
public class EtudiantController {

  private final EtudiantService etudiantService;

  @GetMapping
  public List<Etudiant> getAll() {
    return etudiantService.findAll();
  }

  @GetMapping("/{id}")
  public Etudiant getById(@PathVariable Long id) {
    return etudiantService.findById(id);
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public Etudiant create(@RequestBody Etudiant etudiant) {
    return etudiantService.create(etudiant);
  }

  @PutMapping("/{id}")
  public Etudiant update(@PathVariable Long id, @RequestBody Etudiant etudiant) {
    return etudiantService.update(id, etudiant);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable Long id) {
    etudiantService.delete(id);
  }
}
