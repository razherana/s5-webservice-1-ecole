package mg.razherana.notes.entities;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "inscription")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Inscription {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "date_inscription", nullable = false)
  private LocalDate dateInscription;

  @ManyToOne
  @JoinColumn(name = "annee_scolaire_id", nullable = false)
  private AnneeScolaire anneeScolaire;

  @ManyToOne
  @JoinColumn(name = "semestre_id", nullable = false)
  private Semestre semestre;

  @ManyToOne
  @JoinColumn(name = "etudiant_id", nullable = false)
  private Etudiant etudiant;

  @OneToMany(mappedBy = "inscription", fetch = jakarta.persistence.FetchType.LAZY)
  @JsonIgnore
  private List<Notes> notes;
}
