package mg.razherana.notes.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mg.razherana.notes.entities.Option;

@Repository
public interface OptionRepository extends JpaRepository<Option, Long> {

  List<Option> findBySemestreId(Long semestreId);

  Optional<Option> findBySemestreIdAndLibelleIgnoreCase(Long semestreId, String libelle);
}
