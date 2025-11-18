package mg.razherana.notes.entities;

import java.util.List;

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
@Table(name = "semestre")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Semestre {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, length = 50)
    private String libelle;
    
    @Column(nullable = false, length = 50)
    private String annee;
    
    @OneToMany(mappedBy = "semestre", fetch = jakarta.persistence.FetchType.LAZY)
    private List<UniteEnseignement> unites;
    
    @OneToMany(mappedBy = "semestre", fetch = jakarta.persistence.FetchType.LAZY)
    private List<Inscription> inscriptions;
}
