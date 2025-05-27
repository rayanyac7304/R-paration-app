package reparation.metier;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reparation.dao.entities.Client;
import reparation.dao.entities.Reparateur;
import reparation.dao.repositories.ClientRepository;

import java.util.List;


@AllArgsConstructor
@Service
public class GestionClient {
    private ClientRepository cr;

    @Transactional
    public Client ajouter(Client c) {
        return cr.save(c);
    }

    @Transactional
    public Client modifier(Client c) {
        return cr.save(c);
    }

    @Transactional
    public void supprimer(Client c) {
        cr.delete(c);
    }

    public Client rechercher(Long id) {
        return cr.findById(id).orElseThrow(() -> new RuntimeException("Client non trouvé"));
    }

    public List<Client> lister() {
        return cr.findAll();
    }
    
    public List<Client> listerParReparateur(Long reparateurId) {
        return cr.findByReparateurs_Id(reparateurId);
    }
}
