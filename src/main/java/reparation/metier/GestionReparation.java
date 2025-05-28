package reparation.metier;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import reparation.dao.entities.Reparateur;
import reparation.dao.entities.Reparation;
import reparation.dao.entities.enums.Statut;
import reparation.dao.repositories.ReparationRepository;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class GestionReparation {
    private ReparationRepository rr;

    @Transactional
    public Reparation ajouter(Reparation r) {
        return rr.save(r);
    }

    @Transactional
    public Reparation modifier(Reparation r) {
        return rr.save(r);
    }

    @Transactional
    public void supprimer(Reparation r) {
        rr.delete(r);
    }

    public Reparation rechercher(Long id) {
        return rr.findById(id).orElseThrow(() -> new RuntimeException("reparation non trouvée"));
    }

    public List<Reparation> lister() {
        return rr.findAll();
    }

    public List<Reparation> listerParReparateur(Long idReparateur) {
        return rr.findByReparateurId(idReparateur);
    }
    
    public double totalDesGains() {
        return rr.findAll().stream()
                .mapToDouble(r -> r.getPrix() != null ? r.getPrix() : 0.0)
                .sum();
    }
  
   
    public List<Reparation> listerParStatut(Statut statut) {
        return rr.findByStatutOrderByDateReparationDesc(statut);
    }

    public List<Reparation> listerParClient(Long clientId) {
        return rr.findByClient_Id(clientId);
    }



    public Optional<Reparation> rechercherParCode(String code) {
        return rr.findByCode(code);
    }
    
    public List<Reparation> listerParDateReparationDesc() {
        return rr.findAllByOrderByDateReparationDesc();
    }
}
