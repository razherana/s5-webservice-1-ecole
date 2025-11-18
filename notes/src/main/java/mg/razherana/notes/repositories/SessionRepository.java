package mg.razherana.notes.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mg.razherana.notes.entities.Session;

@Repository
public interface SessionRepository extends JpaRepository<Session, Long> {
}
