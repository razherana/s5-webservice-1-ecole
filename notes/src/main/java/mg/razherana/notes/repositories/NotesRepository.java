package mg.razherana.notes.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mg.razherana.notes.entities.Notes;

@Repository
public interface NotesRepository extends JpaRepository<Notes, Long> {

	List<Notes> findByInscriptionSemestreId(Long semestreId);

	List<Notes> findByInscriptionSemestreIdAndInscriptionEtudiantId(Long semestreId, Long etudiantId);
}
