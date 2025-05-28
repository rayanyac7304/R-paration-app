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
public class ClientAuthController {

    private final GestionReparation gestionReparation;


    // Traitement de la connexion avec code de suivi
    @PostMapping("/login")
    public String authentifierClient(@RequestParam String code, HttpSession session, Model model) {
        if (code == null || code.trim().isEmpty()) {
            model.addAttribute("error", "Veuillez saisir un code de suivi valide");
            return "client/login";
        }

        Optional<Reparation> reparationOpt = gestionReparation.rechercherParCode(code.trim());

        if (reparationOpt.isPresent()) {
            session.setAttribute("reparation", reparationOpt.get());
            
            
            return "redirect:/client/suivi";
        } else {
            model.addAttribute("error", "Aucune réparation trouvée avec ce code : " + code);
            return "client/login";
        }
    }

    // Déconnexion
    @PostMapping("/logout")
    public String deconnecter(HttpSession session) {
        session.invalidate();
        return "redirect:/role-selection";
    }
}