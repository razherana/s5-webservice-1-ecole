package mg.razherana.notes.entities;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "unite_enseignement")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UniteEnseignement {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "code_matiere", nullable = false, length = 50)
  private String codeMatiere;

  @Column(name = "intitule", nullable = false, length = 50)
  private String intitule;

  @OneToMany(mappedBy = "unite", fetch = FetchType.LAZY)
  @JsonIgnore
  private List<Notes> notes;

  @OneToMany(mappedBy = "unite", fetch = FetchType.LAZY)
  @JsonIgnore
  private List<OptionUniteEnseignement> optionAssociations;
}
