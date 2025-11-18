package mg.razherana.notes.entities;

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
@Table(name = "groupe_matiere")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GroupeMatiere {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(length = 50)
  private String libelle;

  @OneToMany(mappedBy = "groupe", fetch = jakarta.persistence.FetchType.LAZY)
  @JsonIgnore
  private List<UniteEnseignement> unites;
}
