package reparation.dao.entities;

import java.util.List;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;
    private String prenom;

    @Column(nullable = false, length = 20)
    private String telephone;

    @ManyToMany
    @JoinTable(
        name = "reparateur_client",
        joinColumns = @JoinColumn(name = "client_id"),
        inverseJoinColumns = @JoinColumn(name = "reparateur_id")
    )
    private List<Reparateur> reparateurs;

}