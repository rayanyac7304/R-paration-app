package reparation.presentation;



import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    @GetMapping("/")
    public String home() {
    	System.out.println("Home page accessed");
        return "redirect:/role-selection";
    	
    }

    @GetMapping("/role-selection")
    public String roleSelection(
            @RequestParam(value = "error", required = false) String error,
            @RequestParam(value = "logout", required = false) String logout,
            @RequestParam(value = "unauthorized", required = false) String unauthorized,
            Model model) {

        if (error != null) {
            model.addAttribute("error", "Invalid username or password.");
        }
        if (logout != null) {
            model.addAttribute("logout", "You have been logged out successfully.");
        }
        if (unauthorized != null) {
            model.addAttribute("unauthorized", "You are not authorized to access this page.");
        }

        return "role-selection";
    }


    
    @GetMapping("/admin/login")
    public String adminLogin() {
        return "admin/login";
    }

    @GetMapping("/reparateur/login")
    public String reparateurLogin() {
        return "reparateur/login";
    }

    @GetMapping("/client/login")
    public String clientLogin() {
        return "client/login"; 
    }
}