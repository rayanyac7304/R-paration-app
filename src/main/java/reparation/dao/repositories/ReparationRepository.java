package reparation.dao.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import reparation.dao.entities.Reparation;
import reparation.dao.entities.enums.Statut;

import java.util.List;
import java.util.Optional;

public interface ReparationRepository extends JpaRepository<Reparation, Long> {
    List<Reparation> findByReparateurId(Long reparateurId);
    List<Reparation> findByClient_Id(Long clientId);
    Optional<Reparation> findByCode(String code);
    List<Reparation> findAllByOrderByDateReparationDesc();
    List<Reparation> findByStatutOrderByDateReparationDesc(Statut statut);

}
