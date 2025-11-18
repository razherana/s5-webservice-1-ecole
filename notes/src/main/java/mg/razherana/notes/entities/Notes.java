package mg.razherana.notes.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Table(name = "notes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Notes {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "valeur", nullable = false)
    private Integer valeur;
    
    @ManyToOne
    @JoinColumn(name = "inscription_id", nullable = false)
    private Inscription inscription;
    
    @ManyToOne
    @JoinColumn(name = "session_id", nullable = false)
    private Session session;
    
    @ManyToOne
    @JoinColumn(name = "unite_id", nullable = false)
    private UniteEnseignement unite;
}
