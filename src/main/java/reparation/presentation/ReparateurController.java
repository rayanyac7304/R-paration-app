package reparation.presentation;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import reparation.dao.entities.*;
import reparation.metier.*;

import java.security.Principal;
import java.util.List;
import java.util.ArrayList;

@Controller
@RequestMapping("/reparateur")
@AllArgsConstructor
public class ReparateurController {

    private final GestionClient gestionClient;
    private final GestionAppareil gestionAppareil;
    private final GestionReparation gestionReparation;
    private final GestionReparateur gestionReparateur;

    // --- ACCUEIL ---
    @GetMapping("")
    public String accueilReparateur() {
        return "reparateur/index";
    }

    // ============== METHODES DE LISTAGE ==============
    @GetMapping("/reparations")
    public String listerSesReparations(Model model, Principal principal) {
        String login = principal.getName();
        Reparateur reparateur = gestionReparateur.rechercherParLogin(login);
        List<Reparation> reparations = gestionReparation.listerParReparateur(reparateur.getId());
        model.addAttribute("reparations", reparations);
        return "reparateur/reparations/liste";
    }

    @GetMapping("/clients")
    public String listerSesClients(Model model, Principal principal) {
        String login = principal.getName();
        Reparateur reparateur = gestionReparateur.rechercherParLogin(login);
        List<Client> clients = gestionClient.listerParReparateur(reparateur.getId());
        model.addAttribute("clients", clients);
        return "reparateur/clients/liste";
    }

    @GetMapping("/appareils")
    public String listerSesAppareils(Model model, Principal principal) {
        String login = principal.getName();
        Reparateur reparateur = gestionReparateur.rechercherParLogin(login);
        List<Appareil> appareils = gestionAppareil.listerParReparateur(reparateur.getId());
        model.addAttribute("appareils", appareils);
        return "reparateur/appareils/liste";
    }

    // ============== METHODES D'AJOUT ==============
    @GetMapping("/clients/nouveau")
    public String formNouveauClient(Model model) {
        model.addAttribute("client", new Client());
        return "reparateur/clients/form";
    }

    @PostMapping("/clients")
    public String enregistrerClient(@Valid @ModelAttribute Client client, BindingResult result, Principal principal) {
        if (result.hasErrors()) return "reparateur/clients/form";

        String login = principal.getName();
        Reparateur reparateur = gestionReparateur.rechercherParLogin(login);

        if (client.getReparateurs() == null) {
            client.setReparateurs(new ArrayList<>());
        }
        
        client.getReparateurs().add(reparateur);

        gestionClient.ajouter(client);
        return "redirect:/reparateur/clients";
    }

    @GetMapping("/appareils/nouveau")
    public String formNouvelAppareil(Model model, Principal principal) {
        String login = principal.getName();
        Reparateur reparateur = gestionReparateur.rechercherParLogin(login);
        model.addAttribute("appareil", new Appareil());
        model.addAttribute("clients", gestionClient.listerParReparateur(reparateur.getId()));
        return "reparateur/appareils/form";
    }

    @PostMapping("/appareils")
    public String enregistrerAppareil(@Valid @ModelAttribute Appareil appareil, BindingResult result, Principal principal, Model model) {
        if (result.hasErrors()) {
            String login = principal.getName();
            Reparateur reparateur = gestionReparateur.rechercherParLogin(login);
            model.addAttribute("clients", gestionClient.listerParReparateur(reparateur.getId()));
            return "reparateur/appareils/form";
        }

        String login = principal.getName();
        Reparateur reparateur = gestionReparateur.rechercherParLogin(login);

        if (appareil.getReparateurs() == null) {
            appareil.setReparateurs(new ArrayList<>());
        }

        appareil.getReparateurs().add(reparateur);

        gestionAppareil.ajouter(appareil);
        return "redirect:/reparateur/appareils";
    }

    @GetMapping("/reparations/nouveau")
    public String formNouvelleReparation(Model model, Principal principal) {
        String login = principal.getName();
        Reparateur reparateur = gestionReparateur.rechercherParLogin(login);
        model.addAttribute("reparation", new Reparation());
        model.addAttribute("clients", gestionClient.listerParReparateur(reparateur.getId()));
        model.addAttribute("appareils", gestionAppareil.listerParReparateur(reparateur.getId()));
        return "reparateur/reparations/form";
    }

    @PostMapping("/reparations")
    public String enregistrerReparation(@Valid @ModelAttribute Reparation reparation, BindingResult result, Principal principal, Model model) {
        if (result.hasErrors()) {
            String login = principal.getName();
            Reparateur reparateur = gestionReparateur.rechercherParLogin(login);
            model.addAttribute("clients", gestionClient.listerParReparateur(reparateur.getId()));
            model.addAttribute("appareils", gestionAppareil.listerParReparateur(reparateur.getId()));
            return "reparateur/reparations/form";
        }
        String login = principal.getName();
        Reparateur reparateur = gestionReparateur.rechercherParLogin(login);
        reparation.setReparateur(reparateur);
        gestionReparation.ajouter(reparation);
        return "redirect:/reparateur/reparations";
    }

    // ============== METHODES DE MODIFICATION ==============
    @GetMapping("/reparations/edit/{id}")
    public String formModifierReparation(@PathVariable Long id, Model model, Principal principal) {
        Reparation reparation = gestionReparation.rechercher(id);
        if (!estLaSienne(reparation, principal)) return "redirect:/reparateur/reparations";
        model.addAttribute("reparation", reparation);
        String login = principal.getName();
        Reparateur reparateur = gestionReparateur.rechercherParLogin(login);
        model.addAttribute("clients", gestionClient.listerParReparateur(reparateur.getId()));
        model.addAttribute("appareils", gestionAppareil.listerParReparateur(reparateur.getId()));
        return "reparateur/reparations/form";
    }

    @PostMapping("/reparations/update")
    public String modifierReparation(@Valid @ModelAttribute Reparation reparation, BindingResult result, Principal principal, Model model) {
        if (result.hasErrors()) {
            String login = principal.getName();
            Reparateur reparateur = gestionReparateur.rechercherParLogin(login);
            model.addAttribute("clients", gestionClient.listerParReparateur(reparateur.getId()));
            model.addAttribute("appareils", gestionAppareil.listerParReparateur(reparateur.getId()));
            return "reparateur/reparations/form";
        }
        if (!estLaSienne(reparation, principal)) return "redirect:/reparateur/reparations";
        gestionReparation.modifier(reparation);
        return "redirect:/reparateur/reparations";
    }

    @GetMapping("/clients/edit/{id}")
    public String formModifierClient(@PathVariable Long id, Model model, Principal principal) {
        Client client = gestionClient.rechercher(id);
        if (!estLeSien(client, principal)) return "redirect:/reparateur/clients";
        model.addAttribute("client", client);
        return "reparateur/clients/form";
    }

    @PostMapping("/clients/update")
    public String modifierClient(@Valid @ModelAttribute Client client, BindingResult result, Principal principal) {
        if (result.hasErrors()) return "reparateur/clients/form";
        if (!estLeSien(client, principal)) return "redirect:/reparateur/clients";
        gestionClient.modifier(client);
        return "redirect:/reparateur/clients";
    }

    @GetMapping("/appareils/edit/{id}")
    public String formModifierAppareil(@PathVariable Long id, Model model, Principal principal) {
        Appareil appareil = gestionAppareil.rechercher(id);
        if (!estLeSien(appareil, principal)) return "redirect:/reparateur/appareils";
        model.addAttribute("appareil", appareil);
        String login = principal.getName();
        Reparateur reparateur = gestionReparateur.rechercherParLogin(login);
        model.addAttribute("clients", gestionClient.listerParReparateur(reparateur.getId()));
        return "reparateur/appareils/form";
    }

    @PostMapping("/appareils/update")
    public String modifierAppareil(@Valid @ModelAttribute Appareil appareil, BindingResult result, Model model, Principal principal) {
        if (result.hasErrors()) {
            String login = principal.getName();
            Reparateur reparateur = gestionReparateur.rechercherParLogin(login);
            model.addAttribute("clients", gestionClient.listerParReparateur(reparateur.getId()));
            return "reparateur/appareils/form";
        }
        if (!estLeSien(appareil, principal)) return "redirect:/reparateur/appareils";
        gestionAppareil.modifier(appareil);
        return "redirect:/reparateur/appareils";
    }

    // ============== METHODES DE SUPPRESSION ==============
    @GetMapping("/clients/delete/{id}")
    public String supprimerClient(@PathVariable Long id, Principal principal) {
        Client client = gestionClient.rechercher(id);
        if (!estLeSien(client, principal)) return "redirect:/reparateur/clients";
        gestionClient.supprimer(client);
        return "redirect:/reparateur/clients";
    }

    @GetMapping("/appareils/delete/{id}")
    public String supprimerAppareil(@PathVariable Long id, Principal principal) {
        Appareil appareil = gestionAppareil.rechercher(id);
        if (!estLeSien(appareil, principal)) return "redirect:/reparateur/appareils";
        gestionAppareil.supprimer(appareil);
        return "redirect:/reparateur/appareils";
    }

    @GetMapping("/reparations/delete/{id}")
    public String supprimerReparation(@PathVariable Long id, Principal principal) {
        Reparation reparation = gestionReparation.rechercher(id);
        if (!estLaSienne(reparation, principal)) return "redirect:/reparateur/reparations";
        gestionReparation.supprimer(reparation);
        return "redirect:/reparateur/reparations";
    }

    // ============== METHODES SPECIFIQUES ==============
    @GetMapping("/gains")
    public String afficherGains(Model model, Principal principal) {
        String login = principal.getName();
        Reparateur reparateur = gestionReparateur.rechercherParLogin(login);

        List<Reparation> mesReparations = gestionReparation.listerParReparateur(reparateur.getId());

        double pourcentage = 0.30;
        double gains = mesReparations.stream()
            .mapToDouble(r -> r.getPrix() * pourcentage)
            .sum();

        model.addAttribute("gains", gains);
        return "reparateur/gains";
    }

    // ============== METHODES UTILITAIRES ==============
    private boolean estLeSien(Client client, Principal principal) {
        String login = principal.getName();
        Reparateur reparateur = gestionReparateur.rechercherParLogin(login);
        return client.getReparateurs().contains(reparateur);
    }

    private boolean estLaSienne(Reparation reparation, Principal principal) {
        String login = principal.getName();
        Reparateur reparateur = gestionReparateur.rechercherParLogin(login);
        return reparation.getReparateur().equals(reparateur);
    }

    private boolean estLeSien(Appareil appareil, Principal principal) {
        String login = principal.getName();
        Reparateur reparateur = gestionReparateur.rechercherParLogin(login);
        return appareil.getReparateurs().contains(reparateur);
    }
}