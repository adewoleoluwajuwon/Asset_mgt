package com.Jguides.spingboot.controller;


import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping
public class HomeController {


    @GetMapping("/")
    public String home() {
// after login, land on dashboard
        return "redirect:/dashboard";
    }


    @GetMapping("/dashboard")
    public String dashboard(Authentication auth, Model model) {
        String username = auth != null ? auth.getName() : "";
        boolean isAdminMgr = auth != null && auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN_MANAGER"));
        boolean isStoreMgr = auth != null && auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_STORE_MANAGER"));
        model.addAttribute("username", username);
        model.addAttribute("isAdminMgr", isAdminMgr);
        model.addAttribute("isStoreMgr", isStoreMgr);
        return "dashboard";
    }


    @GetMapping("/login")
    public String login() {
        return "login"; // thymeleaf template
    }
}