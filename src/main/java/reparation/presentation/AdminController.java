package reparation.presentation;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import reparation.dao.entities.*;
import reparation.metier.*;

@Controller
@RequestMapping("/admin")
@AllArgsConstructor
public class AdminController {

    private final GestionClient gestionClient;
    private final GestionAppareil gestionAppareil;
    private final GestionReparateur gestionReparateur;
    private final GestionReparation gestionReparation;

    // Method to check if admin is logged in
    private boolean isAdminLoggedIn(HttpSession session) {
        return session.getAttribute("admin") != null;
    }

    // --- ACCUEIL ---
    @GetMapping("/index")
    public String accueilAdmin(Model model, HttpSession session) {
        if (!isAdminLoggedIn(session)) {
            return "redirect:/admin/login";
        }
        
        model.addAttribute("totalReparations", gestionReparation.lister().size());
        model.addAttribute("totalClients", gestionClient.lister().size());
        model.addAttribute("totalReparateurs", gestionReparateur.lister().size());
        model.addAttribute("gainTotal", gestionReparation.totalDesGains());
        return "admin/index";
    }
    
    // ============== METHODES DE LISTAGE ==============
    @GetMapping("/clients")
    public String listerClients(Model model, HttpSession session) {
        if (!isAdminLoggedIn(session)) {
            return "redirect:/admin/login";
        }
        model.addAttribute("clients", gestionClient.lister());
        return "admin/clients/liste";
    }

    @GetMapping("/appareils")
    public String listerAppareils(Model model, HttpSession session) {
        if (!isAdminLoggedIn(session)) {
            return "redirect:/admin/login";
        }
        model.addAttribute("appareils", gestionAppareil.lister());
        return "admin/appareils/liste";
    }

    @GetMapping("/reparateurs")
    public String listerReparateurs(Model model, HttpSession session) {
        if (!isAdminLoggedIn(session)) {
            return "redirect:/admin/login";
        }
        model.addAttribute("reparateurs", gestionReparateur.lister());
        return "admin/reparateurs/liste";
    }

    @GetMapping("/reparations")
    public String listerReparations(Model model, HttpSession session) {
        if (!isAdminLoggedIn(session)) {
            return "redirect:/admin/login";
        }
        model.addAttribute("reparations", gestionReparation.lister());
        return "admin/reparations/liste";
    }

    // ============== METHODES D'AJOUT (FORMS) ==============
    @GetMapping("/clients/form")
    public String formNouveauClient(Model model, HttpSession session, @RequestParam(required = false) Long id) {
        if (!isAdminLoggedIn(session)) {
            return "redirect:/admin/login";
        }
        if (id != null) {
            model.addAttribute("client", gestionClient.rechercher(id));
        } else {
            model.addAttribute("client", new Client());
        }
        return "admin/clients/form";
    }

    @GetMapping("/appareils/form")
    public String formNouvelAppareil(Model model, HttpSession session, @RequestParam(required = false) Long id) {
        if (!isAdminLoggedIn(session)) {
            return "redirect:/admin/login";
        }
        if (id != null) {
            model.addAttribute("appareil", gestionAppareil.rechercher(id));
        } else {
            model.addAttribute("appareil", new Appareil());
        }
        model.addAttribute("clients", gestionClient.lister());
        return "admin/appareils/form";
    }

    @GetMapping("/reparateurs/form")
    public String formNouveauReparateur(Model model, HttpSession session, @RequestParam(required = false) Long id) {
        if (!isAdminLoggedIn(session)) {
            return "redirect:/admin/login";
        }
        if (id != null) {
            model.addAttribute("reparateur", gestionReparateur.rechercher(id));
        } else {
            model.addAttribute("reparateur", new Reparateur());
        }
        return "admin/reparateurs/form";
    }

    @GetMapping("/reparations/form")
    public String formNouvelleReparation(Model model, HttpSession session, @RequestParam(required = false) Long id) {
        if (!isAdminLoggedIn(session)) {
            return "redirect:/admin/login";
        }
        if (id != null) {
            model.addAttribute("reparation", gestionReparation.rechercher(id));
        } else {
            model.addAttribute("reparation", new Reparation());
        }
        model.addAttribute("clients", gestionClient.lister());
        model.addAttribute("appareils", gestionAppareil.lister());
        model.addAttribute("reparateurs", gestionReparateur.lister());
        return "admin/reparations/form";
    }

    // ============== METHODES D'ENREGISTREMENT ==============
    @PostMapping("/clients/save")
    public String enregistrerClient(@Valid @ModelAttribute Client client, BindingResult result, HttpSession session) {
        if (!isAdminLoggedIn(session)) {
            return "redirect:/admin/login";
        }
        if (result.hasErrors()) return "admin/clients/form";
        
        if (client.getId() != null) {
            gestionClient.modifier(client);
        } else {
            gestionClient.ajouter(client);
        }
        return "redirect:/admin/clients";
    }

    @PostMapping("/appareils/save")
    public String enregistrerAppareil(@Valid @ModelAttribute Appareil appareil, 
                                     BindingResult result, Model model, HttpSession session) {
        if (!isAdminLoggedIn(session)) {
            return "redirect:/admin/login";
        }
        if (result.hasErrors()) {
            model.addAttribute("clients", gestionClient.lister());
            return "admin/appareils/form";
        }
        
        if (appareil.getId() != null) {
            gestionAppareil.modifier(appareil);
        } else {
            gestionAppareil.ajouter(appareil);
        }
        return "redirect:/admin/appareils";
    }

    @PostMapping("/reparateurs/save")
    public String enregistrerReparateur(@Valid @ModelAttribute Reparateur reparateur, 
                                        BindingResult result, HttpSession session) {
        if (!isAdminLoggedIn(session)) {
            return "redirect:/admin/login";
        }
        if (result.hasErrors()) return "admin/reparateurs/form";
        
        if (reparateur.getId() != null) {
            gestionReparateur.modifier(reparateur);
        } else {
            gestionReparateur.ajouter(reparateur);
        }
        return "redirect:/admin/reparateurs";
    }

    @PostMapping("/reparations/save")
    public String enregistrerReparation(@Valid @ModelAttribute Reparation reparation,
                                       BindingResult result, Model model, HttpSession session) {
        if (!isAdminLoggedIn(session)) {
            return "redirect:/admin/login";
        }
        if (result.hasErrors()) {
            model.addAttribute("clients", gestionClient.lister());
            model.addAttribute("appareils", gestionAppareil.lister());
            model.addAttribute("reparateurs", gestionReparateur.lister());
            return "admin/reparations/form";
        }
        
        if (reparation.getId() != null) {
            gestionReparation.modifier(reparation);
        } else {
            gestionReparation.ajouter(reparation);
        }
        return "redirect:/admin/reparations";
    }

    // ============== METHODES DE MODIFICATION (Old URLs for compatibility) ==============
    @GetMapping("/clients/edit/{id}")
    public String formModifierClient(@PathVariable Long id, Model model, HttpSession session) {
        if (!isAdminLoggedIn(session)) {
            return "redirect:/admin/login";
        }
        model.addAttribute("client", gestionClient.rechercher(id));
        return "admin/clients/form";
    }

    @GetMapping("/appareils/edit/{id}")
    public String formModifierAppareil(@PathVariable Long id, Model model, HttpSession session) {
        if (!isAdminLoggedIn(session)) {
            return "redirect:/admin/login";
        }
        model.addAttribute("appareil", gestionAppareil.rechercher(id));
        model.addAttribute("clients", gestionClient.lister());
        return "admin/appareils/form";
    }

    @GetMapping("/reparateurs/edit/{id}")
    public String formModifierReparateur(@PathVariable Long id, Model model, HttpSession session) {
        if (!isAdminLoggedIn(session)) {
            return "redirect:/admin/login";
        }
        model.addAttribute("reparateur", gestionReparateur.rechercher(id));
        return "admin/reparateurs/form";
    }

    @GetMapping("/reparations/edit/{id}")
    public String formModifierReparation(@PathVariable Long id, Model model, HttpSession session) {
        if (!isAdminLoggedIn(session)) {
            return "redirect:/admin/login";
        }
        model.addAttribute("reparation", gestionReparation.rechercher(id));
        model.addAttribute("clients", gestionClient.lister());
        model.addAttribute("appareils", gestionAppareil.lister());
        model.addAttribute("reparateurs", gestionReparateur.lister());
        return "admin/reparations/form";
    }

    // Legacy POST methods (keeping for compatibility)
    @PostMapping("/clients")
    public String enregistrerClientLegacy(@Valid @ModelAttribute Client client, BindingResult result, HttpSession session) {
        return enregistrerClient(client, result, session);
    }

    @PostMapping("/appareils")
    public String enregistrerAppareilLegacy(@Valid @ModelAttribute Appareil appareil, 
                                           BindingResult result, Model model, HttpSession session) {
        return enregistrerAppareil(appareil, result, model, session);
    }

    @PostMapping("/reparateurs")
    public String enregistrerReparateurLegacy(@Valid @ModelAttribute Reparateur reparateur, 
                                             BindingResult result, HttpSession session) {
        return enregistrerReparateur(reparateur, result, session);
    }

    @PostMapping("/reparations")
    public String enregistrerReparationLegacy(@Valid @ModelAttribute Reparation reparation,
                                             BindingResult result, Model model, HttpSession session) {
        return enregistrerReparation(reparation, result, model, session);
    }

    @PostMapping("/clients/update")
    public String modifierClient(@Valid @ModelAttribute Client client, BindingResult result, HttpSession session) {
        return enregistrerClient(client, result, session);
    }

    @PostMapping("/appareils/update")
    public String modifierAppareil(@Valid @ModelAttribute Appareil appareil, 
                                  BindingResult result, Model model, HttpSession session) {
        return enregistrerAppareil(appareil, result, model, session);
    }

    @PostMapping("/reparateurs/update")
    public String modifierReparateur(@Valid @ModelAttribute Reparateur reparateur, 
                                    BindingResult result, HttpSession session) {
        return enregistrerReparateur(reparateur, result, session);
    }

    @PostMapping("/reparations/update")
    public String modifierReparation(@Valid @ModelAttribute Reparation reparation,
                                    BindingResult result, Model model, HttpSession session) {
        return enregistrerReparation(reparation, result, model, session);
    }

    // ============== METHODES DE SUPPRESSION ==============
    @GetMapping("/clients/delete/{id}")
    public String supprimerClient(@PathVariable Long id, HttpSession session) {
        if (!isAdminLoggedIn(session)) {
            return "redirect:/admin/login";
        }
        gestionClient.supprimer(gestionClient.rechercher(id));
        return "redirect:/admin/clients";
    }

    @GetMapping("/appareils/delete/{id}")
    public String supprimerAppareil(@PathVariable Long id, HttpSession session) {
        if (!isAdminLoggedIn(session)) {
            return "redirect:/admin/login";
        }
        gestionAppareil.supprimer(gestionAppareil.rechercher(id));
        return "redirect:/admin/appareils";
    }

    @GetMapping("/reparateurs/delete/{id}")
    public String supprimerReparateur(@PathVariable Long id, HttpSession session) {
        if (!isAdminLoggedIn(session)) {
            return "redirect:/admin/login";
        }
        gestionReparateur.supprimer(gestionReparateur.rechercher(id));
        return "redirect:/admin/reparateurs";
    }

    @GetMapping("/reparations/delete/{id}")
    public String supprimerReparation(@PathVariable Long id, HttpSession session) {
        if (!isAdminLoggedIn(session)) {
            return "redirect:/admin/login";
        }
        gestionReparation.supprimer(gestionReparation.rechercher(id));
        return "redirect:/admin/reparations";
    }

    // ============== METHODES SPECIFIQUES ==============
    @GetMapping("/reparations/gains")
    public String afficherGainsTotaux(Model model, HttpSession session) {
        if (!isAdminLoggedIn(session)) {
            return "redirect:/admin/login";
        }
        double total = gestionReparation.totalDesGains();
        model.addAttribute("gains", total);
        return "admin/reparations/gains";
    }
}