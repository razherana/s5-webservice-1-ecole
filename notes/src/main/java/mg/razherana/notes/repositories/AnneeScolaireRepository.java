package mg.razherana.notes.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mg.razherana.notes.entities.AnneeScolaire;

@Repository
public interface AnneeScolaireRepository extends JpaRepository<AnneeScolaire, Long> {
}
