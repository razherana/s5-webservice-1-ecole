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
import mg.razherana.notes.entities.AnneeScolaire;
import mg.razherana.notes.services.AnneeScolaireService;

@RestController
@RequestMapping("/api/annees-scolaires")
@RequiredArgsConstructor
public class AnneeScolaireController {

  private final AnneeScolaireService anneeScolaireService;

  @GetMapping
  public List<AnneeScolaire> getAll() {
    return anneeScolaireService.findAll();
  }

  @GetMapping("/{id}")
  public AnneeScolaire getById(@PathVariable Long id) {
    return anneeScolaireService.findById(id);
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public AnneeScolaire create(@RequestBody AnneeScolaire anneeScolaire) {
    return anneeScolaireService.create(anneeScolaire);
  }

  @PutMapping("/{id}")
  public AnneeScolaire update(@PathVariable Long id, @RequestBody AnneeScolaire anneeScolaire) {
    return anneeScolaireService.update(id, anneeScolaire);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable Long id) {
    anneeScolaireService.delete(id);
  }
}
