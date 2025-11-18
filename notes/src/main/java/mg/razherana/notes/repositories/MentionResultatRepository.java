package mg.razherana.notes.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mg.razherana.notes.entities.MentionResultat;

@Repository
public interface MentionResultatRepository extends JpaRepository<MentionResultat, Long> {
}
