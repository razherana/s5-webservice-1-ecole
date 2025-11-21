package mg.razherana.notes.repositories;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import mg.razherana.notes.entities.Inscription;

@Repository
public interface InscriptionRepository extends JpaRepository<Inscription, Long> {

	@EntityGraph(attributePaths = {"etudiant", "semestre", "anneeScolaire", "notes", "notes.unite"})
	@Query("""
			select distinct i from Inscription i
			where i.semestre.id in :semestreIds
				and (:anneeScolaireId is null or i.anneeScolaire.id = :anneeScolaireId)
			""")
	List<Inscription> findBySemestreIdsWithNotes(@Param("semestreIds") Collection<Long> semestreIds,
			@Param("anneeScolaireId") Long anneeScolaireId);
}
