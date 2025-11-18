package mg.razherana.notes.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mg.razherana.notes.entities.GroupeMatiere;

@Repository
public interface GroupeMatiereRepository extends JpaRepository<GroupeMatiere, Long> {

	Optional<GroupeMatiere> findByLibelle(String libelle);
}
