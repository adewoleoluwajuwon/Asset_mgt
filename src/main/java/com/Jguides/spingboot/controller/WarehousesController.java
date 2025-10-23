package com.Jguides.spingboot.controller;

import com.Jguides.spingboot.Model.Warehouse;
import com.Jguides.spingboot.service.WarehouseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
@RequestMapping("/warehouses")
public class WarehousesController {

    private final WarehouseService service;

    @GetMapping({"", "/"})
    public String list(Model model) {
        model.addAttribute("warehouses", service.getAllWarehouses());
        return "warehouses/list";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("warehouse", new Warehouse());
        model.addAttribute("mode", "create");
        return "warehouses/form";
    }

    @PostMapping
    public String create(@ModelAttribute("warehouse") Warehouse w, RedirectAttributes ra) {
        service.saveWarehouse(w);
        ra.addFlashAttribute("ok", "Warehouse created.");
        return "redirect:/warehouses";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Integer id, Model model, RedirectAttributes ra) {
        Warehouse w = service.getWarehouseById(id);
        if (w == null) {
            ra.addFlashAttribute("error", "Warehouse not found.");
            return "redirect:/warehouses";
        }
        model.addAttribute("warehouse", w);
        model.addAttribute("mode", "edit");
        return "warehouses/form";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable Integer id,
                         @ModelAttribute("warehouse") Warehouse w,
                         RedirectAttributes ra) {
        w.setId(id);
        service.saveWarehouse(w);
        ra.addFlashAttribute("ok", "Warehouse updated.");
        return "redirect:/warehouses";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Integer id, RedirectAttributes ra) {
        service.deleteWarehouse(id);
        ra.addFlashAttribute("ok", "Warehouse deleted.");
        return "redirect:/warehouses";
    }
}
