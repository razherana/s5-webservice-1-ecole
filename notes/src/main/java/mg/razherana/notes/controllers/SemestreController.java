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
import mg.razherana.notes.entities.Semestre;
import mg.razherana.notes.services.SemestreService;

@RestController
@RequestMapping("/api/semestres")
@RequiredArgsConstructor
public class SemestreController {

  private final SemestreService semestreService;

  @GetMapping
  public List<Semestre> getAll() {
    return semestreService.findAll();
  }

  @GetMapping("/{id}")
  public Semestre getById(@PathVariable Long id) {
    return semestreService.findById(id);
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public Semestre create(@RequestBody Semestre semestre) {
    return semestreService.create(semestre);
  }

  @PutMapping("/{id}")
  public Semestre update(@PathVariable Long id, @RequestBody Semestre semestre) {
    return semestreService.update(id, semestre);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable Long id) {
    semestreService.delete(id);
  }
}
