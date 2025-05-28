package reparation.dao.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import reparation.dao.entities.Reparateur;
import reparation.dao.entities.Reparation;

public interface ReparateurRepository extends JpaRepository<Reparateur, Long> {
	Optional<Reparateur> findByLogin(String login);
	

}
