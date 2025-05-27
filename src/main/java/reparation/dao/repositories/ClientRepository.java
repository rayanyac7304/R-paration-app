package reparation.dao.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import reparation.dao.entities.Client;
import reparation.dao.entities.Reparateur;

public interface ClientRepository extends JpaRepository<Client, Long> {
	List<Client> findByReparateurs_Id(Long reparateurId);
}
