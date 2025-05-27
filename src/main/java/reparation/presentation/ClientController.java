package reparation.presentation;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import reparation.dao.entities.Reparation;
import reparation.metier.GestionReparation;

import java.util.Optional;

@Controller
@RequestMapping("/client") // ⬅️ point d'entrée client
@AllArgsConstructor
public class ClientController {

    private final GestionReparation gestionReparation;

    // Accueil général (redirige vers login)
    @GetMapping("/")
    public String accueil() {
        return "login"; // Ce n'est pas spécifique au client
    }

    // Interface client principale
    @GetMapping("")
    public String pageClient() {
        return "client/suivi";
    }

    // Consultation du statut de réparation par code (GET)
    @GetMapping("/suivi")
    public String consulterReparation(@RequestParam(required = false) String code, Model model) {
        if (code == null || code.trim().isEmpty()) {
            model.addAttribute("erreur", "Veuillez saisir un code de suivi valide");
            return "client/suivi";
        }

        Optional<Reparation> reparationOpt = gestionReparation.rechercherParCode(code.trim());

        if (reparationOpt.isPresent()) {
            Reparation reparation = reparationOpt.get();
            model.addAttribute("reparation", reparation);
            model.addAttribute("client", reparation.getClient());
            model.addAttribute("appareil", reparation.getAppareil());
            model.addAttribute("reparateur", reparation.getReparateur());
            return "client/resultat";
        } else {
            model.addAttribute("erreur", "Aucune réparation trouvée avec ce code : " + code);
            return "client/suivi";
        }
    }

    // Consultation par code (POST)
    @PostMapping("/suivi")
    public String consulterReparationPost(@RequestParam String code, Model model) {
        return consulterReparation(code, model);
    }
}
