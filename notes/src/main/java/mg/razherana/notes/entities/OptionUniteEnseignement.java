package mg.razherana.notes.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "option_unite_enseignement")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OptionUniteEnseignement {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JsonIgnore
  @JoinColumn(name = "option_id", nullable = false)
  private Option option;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "unite_enseignement_id", nullable = false)
  private UniteEnseignement unite;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "groupe_id", nullable = false)
  private GroupeMatiere groupe;

  @Column(name = "credits", nullable = false)
  private Integer credits;
}
