package com.Jguides.spingboot.controller;

import com.Jguides.spingboot.Model.User;
import com.Jguides.spingboot.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Set;

@Controller
@RequiredArgsConstructor
@RequestMapping("/users")
@PreAuthorize("hasRole('ADMIN_MANAGER')") // all routes here require ADMIN_MANAGER
public class UsersController {

    private final UserService service;
    private static final Set<String> ROLES = Set.of("ADMIN_MANAGER", "STORE_MANAGER", "USER");

    // List + search
    @GetMapping({"", "/"})
    public String list(@RequestParam(value = "keyword", required = false) String keyword, Model model) {
        List<User> users = (keyword == null || keyword.isBlank())
                ? service.getAllUsers()
                : service.searchUser(keyword.trim());

        model.addAttribute("users", users);
        model.addAttribute("keyword", keyword);
        return "users/list";
    }

    // Create form
    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("roles", ROLES);
        model.addAttribute("mode", "create");
        return "users/form";
    }

    // Create submit
    @PostMapping
    public String create(@ModelAttribute("user") User user,
                         @RequestParam("password") String rawPassword,
                         RedirectAttributes ra) {
        if (!ROLES.contains(user.getRole())) {
            ra.addFlashAttribute("error", "Invalid role selected");
            return "redirect:/users/new";
        }
        if (rawPassword == null || rawPassword.isBlank()) {
            ra.addFlashAttribute("error", "Password is required");
            return "redirect:/users/new";
        }
        // encode happens in controller to keep entity simple
        // Will be auto-wired via SecurityConfig's PasswordEncoder
        // (inject via constructor)
        return saveEncoded(user, rawPassword, ra, true);
    }

    // Edit form
    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model, RedirectAttributes ra) {
        User u = service.getUserById(id);
        if (u == null) {
            ra.addFlashAttribute("error", "User not found.");
            return "redirect:/users";
        }
        model.addAttribute("user", u);
        model.addAttribute("roles", ROLES);
        model.addAttribute("mode", "edit");
        return "users/form";
    }

    // Update submit
    @PostMapping("/{id}")
    public String update(@PathVariable Long id,
                         @ModelAttribute("user") User user,
                         @RequestParam(value = "password", required = false) String rawPassword,
                         RedirectAttributes ra) {
        user.setId(id);
        if (!ROLES.contains(user.getRole())) {
            ra.addFlashAttribute("error", "Invalid role selected");
            return "redirect:/users/" + id + "/edit";
        }
        return saveEncoded(user, rawPassword, ra, false);
    }

    // Delete (block self-delete)
    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id, Authentication auth, RedirectAttributes ra) {
        User u = service.getUserById(id);
        if (u == null) {
            ra.addFlashAttribute("error", "User not found.");
            return "redirect:/users";
        }
        if (auth != null && u.getUserName().equalsIgnoreCase(auth.getName())) {
            ra.addFlashAttribute("error", "You cannot delete your own account.");
            return "redirect:/users";
        }
        service.deleteUser(id);
        ra.addFlashAttribute("ok", "User deleted.");
        return "redirect:/users";
    }

    // ---- helpers ----

    private final org.springframework.security.crypto.password.PasswordEncoder encoder;

    private String saveEncoded(User user, String rawPassword, RedirectAttributes ra, boolean isCreate) {
        if (rawPassword != null && !rawPassword.isBlank()) {
            user.setPasswordHash(encoder.encode(rawPassword));
        } else if (isCreate) {
            ra.addFlashAttribute("error", "Password is required");
            return "redirect:/users/new";
        } else {
            // editing with blank password: keep existing hash (handled in service)
            user.setPasswordHash(null);
        }

        try {
            service.saveUser(user);
            ra.addFlashAttribute("ok", isCreate ? "User created." : "User updated.");
            return "redirect:/users";
        } catch (IllegalStateException ex) {
            ra.addFlashAttribute("error", ex.getMessage());
            return isCreate ? "redirect:/users/new" : "redirect:/users/" + user.getId() + "/edit";
        }
    }
}
