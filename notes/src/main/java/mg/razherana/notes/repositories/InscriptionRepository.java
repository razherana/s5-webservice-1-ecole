package mg.razherana.notes.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mg.razherana.notes.entities.Inscription;

@Repository
public interface InscriptionRepository extends JpaRepository<Inscription, Long> {
}
