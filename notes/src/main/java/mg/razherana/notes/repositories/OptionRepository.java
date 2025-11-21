package mg.razherana.notes.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import mg.razherana.notes.entities.Option;

@Repository
public interface OptionRepository extends JpaRepository<Option, Long> {

  List<Option> findBySemestreId(Long semestreId);

  Optional<Option> findBySemestreIdAndLibelleIgnoreCase(Long semestreId, String libelle);

  @EntityGraph(attributePaths = {"optionUnites", "optionUnites.unite", "optionUnites.groupe", "semestre"})
  @Query("""
      select distinct o from Option o
      where (:optionId is null or o.id = :optionId)
        and (:semestreId is null or o.semestre.id = :semestreId)
      order by o.id
      """)
  List<Option> findAllWithAssociations(@Param("optionId") Long optionId, @Param("semestreId") Long semestreId);
}
