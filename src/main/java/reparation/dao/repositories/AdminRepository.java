package reparation.dao.repositories;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import reparation.dao.entities.Admin;

public interface AdminRepository extends JpaRepository<Admin, Long> {
    Optional<Admin> findByLoginAndPassword(String login, String password);

}
