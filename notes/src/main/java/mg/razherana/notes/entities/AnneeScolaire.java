package mg.razherana.notes.entities;

import java.time.LocalDate;

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
@Table(name = "annee_scolaire")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnneeScolaire {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "annee1")
    private LocalDate annee1;
    
    @Column(name = "annee2", nullable = false)
    private LocalDate annee2;
}
