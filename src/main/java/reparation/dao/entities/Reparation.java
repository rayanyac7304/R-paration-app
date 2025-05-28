package reparation.dao.entities;

import jakarta.persistence.*;
import lombok.*;
import reparation.dao.entities.enums.Statut;

import java.time.LocalDateTime;

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

    @Column(unique = true, nullable = false)
    private String code;

    @ManyToOne
    private Appareil appareil;

    @ManyToOne
    private Reparateur reparateur;

    @ManyToOne
    private Client client;
    

    @Column(nullable = false)
    private LocalDateTime dateReparation;

  
}
