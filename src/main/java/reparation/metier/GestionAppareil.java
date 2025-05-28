package reparation.metier;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reparation.dao.entities.Appareil;
import reparation.dao.entities.Reparateur;
import reparation.dao.repositories.AppareilRepository;

import java.util.List;

@AllArgsConstructor
@Service
public class GestionAppareil {
    private AppareilRepository ar;

    @Transactional
    public Appareil ajouter(Appareil a) {
        return ar.save(a);
    }

    @Transactional
    public Appareil modifier(Appareil a) {
        return ar.save(a);
    }

    @Transactional
    public void supprimer(Appareil a) {
        ar.delete(a);
    }

    public Appareil rechercher(Long id) {
        return ar.findById(id).orElseThrow(() -> new RuntimeException("Appareil non trouvé"));
    }

    public List<Appareil> lister() {
        return ar.findAll();
    }
    
}
