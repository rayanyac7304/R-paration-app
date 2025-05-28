package reparation.dao.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import reparation.dao.entities.Appareil;
import reparation.dao.entities.Reparateur;

import java.util.List;

public interface AppareilRepository extends JpaRepository<Appareil, Long> {
    List<Appareil> findByClientId(Long clientId);
}