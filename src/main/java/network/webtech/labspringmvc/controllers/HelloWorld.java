package network.webtech.labspringmvc.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class HelloWorld {
    
    @GetMapping("/")
    public String index (Model model) {
        model.addAttribute("message", "Bem vindo ao Lab Spring MVC!");
        return "home";
    }

    @GetMapping("/message/{msg}")
    public String message (@PathVariable (value="msg") String msg, Model model) {
        model.addAttribute("message", msg);
        return "home";
    }
    
}
