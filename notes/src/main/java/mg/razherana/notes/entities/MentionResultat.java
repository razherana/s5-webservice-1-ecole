package mg.razherana.notes.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "mention_resultat")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MentionResultat {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, length = 50)
    private String libelle;
    
    @Column(name = "moyenne_min", nullable = false)
    private Integer moyenneMin;
    
    @Column(name = "moyenne_max", nullable = false)
    private Integer moyenneMax;
}
