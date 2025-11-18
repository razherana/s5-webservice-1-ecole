package mg.razherana.notes.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mg.razherana.notes.entities.UniteEnseignement;

@Repository
public interface UniteEnseignementRepository extends JpaRepository<UniteEnseignement, Long> {

	Optional<UniteEnseignement> findByCodeMatiere(String codeMatiere);
}
