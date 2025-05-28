package reparation.presentation;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import reparation.dao.entities.Admin;
import reparation.dao.repositories.AdminRepository;

@Controller
@RequestMapping("/admin")
public class AdminAuthController {

    @Autowired
    private AdminRepository adminRepository;

    @PostMapping("/login")
    public String login(@RequestParam String login, @RequestParam String password, HttpSession session) {
        Optional<Admin> adminOpt = adminRepository.findByLoginAndPassword(login, password);
        if (adminOpt.isPresent()) {
            session.setAttribute("admin", adminOpt.get());
            return "redirect:/admin/index";
        } else {
            return "redirect:/admin/login?error=true";
        }
    }

    @PostMapping("/logout")
    public String logout(HttpSession session) {
  
    	session.invalidate();
        return "redirect:/role-selection";
    }

}