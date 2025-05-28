package reparation.metier;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reparation.dao.entities.Reparateur;
import reparation.dao.entities.Reparation;
import reparation.dao.repositories.ReparateurRepository;

import java.util.List;

@AllArgsConstructor
@Service
public class GestionReparateur {
    private ReparateurRepository rr;

    @Transactional
    public Reparateur ajouter(Reparateur r) {
        return rr.save(r);
    }

    @Transactional
    public Reparateur modifier(Reparateur r) {
        return rr.save(r);
    }

    @Transactional
    public void supprimer(Reparateur r) {
        rr.delete(r);
    }

    public Reparateur rechercher(Long id) {
        return rr.findById(id).orElseThrow(() -> new RuntimeException("Réparateur non trouvé"));
    }

    public List<Reparateur> lister() {
        return rr.findAll();
    }
    
    public Reparateur rechercherParLogin(String login) {
        return rr.findByLogin(login).orElseThrow(() -> new RuntimeException("Réparateur non trouvé"));
    }
    
   
    
}
