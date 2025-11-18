package mg.razherana.notes.entities;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "session")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Session {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, length = 50)
  private String libelle;

  @Column(name = "date_session", nullable = false)
  private LocalDate dateSession;

  @OneToMany(mappedBy = "session", fetch = jakarta.persistence.FetchType.LAZY)
  @JsonIgnore
  private List<Notes> notes;
}
