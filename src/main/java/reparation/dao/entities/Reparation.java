package reparation.dao.entities;

import jakarta.persistence.*;
import lombok.*;
import reparation.dao.entities.enums.Probleme;
import reparation.dao.entities.enums.Statut;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Reparation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double prix;

    @Enumerated(EnumType.STRING)
    private Statut statut;


    @Column(unique = true)
    private String code;

    @ManyToOne
    private Appareil appareil;

    @ManyToOne
    private Reparateur reparateur;

    @ManyToOne
    private Client client;
   
	
}
