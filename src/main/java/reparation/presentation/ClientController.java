package reparation.presentation;

import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import reparation.dao.entities.Reparation;
import reparation.metier.GestionReparation;

import java.util.Optional;

@Controller
@RequestMapping("/client")
@AllArgsConstructor
public class ClientController {

    private final GestionReparation gestionReparation;

    // Method to check if client is logged in
    private boolean isClientLoggedIn(HttpSession session) {
        return session.getAttribute("reparation") != null;
    }


   
    @GetMapping("/suivi")
    public String listerReparation(HttpSession session, Model model) {
        Reparation reparationSession = (Reparation) session.getAttribute("reparation");

        if (reparationSession == null) {
            return "redirect:/client/login";
        }

        // Optionnel : rafraîchir les données si elles peuvent changer entre temps
        Optional<Reparation> reparationOpt = gestionReparation.rechercherParCode(reparationSession.getCode());

        if (reparationOpt.isPresent()) {
            Reparation reparation = reparationOpt.get();
            session.setAttribute("reparation", reparation); // Si tu veux maintenir la session à jour

            model.addAttribute("reparation", reparation);
            model.addAttribute("client", reparation.getClient());
            model.addAttribute("appareil", reparation.getAppareil());
            model.addAttribute("reparateur", reparation.getReparateur());
            return "client/suivi";
        }

        // Si le code n'existe plus (supprimé ?)
        session.invalidate();
        model.addAttribute("error", "Réparation introuvable. Veuillez ressaisir le code.");
        return "client/login";
    }

}