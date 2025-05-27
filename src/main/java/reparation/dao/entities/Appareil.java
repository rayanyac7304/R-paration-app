package reparation.dao.entities;

import java.util.List;

import jakarta.persistence.*;
import lombok.*;
import reparation.dao.entities.enums.Probleme;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Appareil {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100)
    private String nom;  
    @ManyToOne
    private Client client;
    
    @Enumerated(EnumType.STRING)
    private Probleme probleme;

    @ManyToMany
    private List<Reparateur> reparateurs;

    
    public void setNom(String nom) {
        this.nom = formatNom(nom);
    }

    private String formatNom(String nom) {
        if (nom == null) return null;
        String[] mots = nom.trim().toLowerCase().split("\\s+");
        StringBuilder sb = new StringBuilder();
        for (String mot : mots) {
            if (!mot.isEmpty()) {
                sb.append(Character.toUpperCase(mot.charAt(0)))
                  .append(mot.substring(1))
                  .append(" ");
            }
        }
        return sb.toString().trim();
    }
}
