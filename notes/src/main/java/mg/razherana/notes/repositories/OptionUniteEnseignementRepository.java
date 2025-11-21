package mg.razherana.notes.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mg.razherana.notes.entities.OptionUniteEnseignement;

@Repository
public interface OptionUniteEnseignementRepository extends JpaRepository<OptionUniteEnseignement, Long> {

  List<OptionUniteEnseignement> findByOptionId(Long optionId);

  Optional<OptionUniteEnseignement> findByOptionIdAndUniteId(Long optionId, Long uniteId);

  boolean existsByOptionIdAndUniteId(Long optionId, Long uniteId);

  void deleteByOptionIdAndUniteId(Long optionId, Long uniteId);
}
