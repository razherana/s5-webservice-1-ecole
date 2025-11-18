package mg.razherana.notes.entities;

import java.time.LocalDate;
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
@Table(name = "etudiant")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Etudiant {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, length = 50)
    private String nom;
    
    @Column(nullable = false, length = 50)
    private String prenom;
    
    @Column(name = "date_naissance")
    private LocalDate dateNaissance;
    
    @OneToMany(mappedBy = "etudiant", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Inscription> inscriptions;
}
