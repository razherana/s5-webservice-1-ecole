package mg.razherana.notes.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mg.razherana.notes.entities.GroupeMatiere;

@Repository
public interface GroupeMatiereRepository extends JpaRepository<GroupeMatiere, Long> {
}
