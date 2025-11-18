package mg.razherana.notes.entities;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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
    
    @Column(nullable = true)
    private Integer credits;
    
    @ManyToOne
    @JoinColumn(name = "groupe_id", nullable = false)
    private GroupeMatiere groupe;
    
    @ManyToOne
    @JoinColumn(name = "semestre_id", nullable = false)
    private Semestre semestre;
    
    @OneToMany(mappedBy = "unite", fetch = FetchType.LAZY)
    private List<Notes> notes;
}
