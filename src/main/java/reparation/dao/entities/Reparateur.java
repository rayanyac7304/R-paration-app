package reparation.dao.entities;

import java.util.List;

import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Reparateur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;
    private String prenom;

    @Column(nullable = false, unique = true, length = 50)
    private String login;

    @Column(nullable = false, length = 100)
    private String password;

    @Column(nullable = false, unique = true, length = 20)
    private String cin;
    
    @ManyToMany(mappedBy = "reparateurs")
    private List<Client> clients;
    
    @ManyToMany(mappedBy = "reparateurs")
    private List<Appareil> appareils;
}
