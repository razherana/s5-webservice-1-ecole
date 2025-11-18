package mg.razherana.notes.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mg.razherana.notes.entities.Semestre;

@Repository
public interface SemestreRepository extends JpaRepository<Semestre, Long> {
}
