package com.Jguides.spingboot.controller;

import com.Jguides.spingboot.Model.AssetStatus;
import com.Jguides.spingboot.repository.AssetRepository;
import com.Jguides.spingboot.service.AssetStatusService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/statuses")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('ADMIN_MANAGER','STORE_MANAGER')")
public class AssetStatusesController {

    private final AssetStatusService assetStatusService;
    private final AssetRepository assetRepository;

    @GetMapping("/")
    public String list(@RequestParam(value = "keyword", required = false) String keyword, Model model) {
        List<AssetStatus> statuses = StringUtils.hasText(keyword)
                ? assetStatusService.searchAssetStatus(keyword)
                : assetStatusService.getAllAssetStatus();

        // Precompute "in use" flags once per status id for the template
        Map<Long, Boolean> inUseMap = new LinkedHashMap<>();
        for (AssetStatus s : statuses) {
            long count = assetRepository.countByStatus_Id(s.getId());
            inUseMap.put(s.getId(), count > 0);
        }

        model.addAttribute("statuses", statuses);
        model.addAttribute("inUseMap", inUseMap);
        model.addAttribute("keyword", keyword);
        return "statuses/list";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("status", new AssetStatus());
        model.addAttribute("isEdit", false);
        model.addAttribute("inUse", false);
        model.addAttribute("pageTitle", "Create Status");
        return "statuses/form";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable("id") Long id, RedirectAttributes ra, Model model) {
        AssetStatus s = assetStatusService.getAssetStatusById(id);
        if (s == null) {
            ra.addFlashAttribute("error", "Status not found.");
            return "redirect:/statuses/";
        }
        boolean inUse = assetRepository.countByStatus_Id(id) > 0;

        model.addAttribute("status", s);
        model.addAttribute("isEdit", true);
        model.addAttribute("inUse", inUse);
        model.addAttribute("pageTitle", "Edit Status");
        return "statuses/form";
    }

    @PostMapping
    public String create(@Valid @ModelAttribute("status") AssetStatus status,
                         BindingResult bind,
                         RedirectAttributes ra,
                         Model model) {

        if (!StringUtils.hasText(status.getStatusName())) {
            bind.rejectValue("statusName", "NotBlank", "Status name is required.");
        }

        if (bind.hasErrors()) {
            model.addAttribute("isEdit", false);
            model.addAttribute("inUse", false);
            model.addAttribute("pageTitle", "Create Status");
            return "statuses/form";
        }

        try {
            assetStatusService.saveAssetStatus(status);
            ra.addFlashAttribute("success", "Status created successfully.");
            return "redirect:/statuses/";
        } catch (Exception e) {
            bind.rejectValue("statusName", "Duplicate", "Status name must be unique.");
            model.addAttribute("isEdit", false);
            model.addAttribute("inUse", false);
            model.addAttribute("pageTitle", "Create Status");
            return "statuses/form";
        }
    }

    @PostMapping("/update/{id}")
    public String update(@PathVariable("id") Long id,
                         @Valid @ModelAttribute("status") AssetStatus status,
                         BindingResult bind,
                         RedirectAttributes ra,
                         Model model) {

        AssetStatus existing = assetStatusService.getAssetStatusById(id);
        if (existing == null) {
            ra.addFlashAttribute("error", "Status not found.");
            return "redirect:/statuses/";
        }

        long usage = assetRepository.countByStatus_Id(id);
        boolean inUse = usage > 0;
        if (inUse) {
            ra.addFlashAttribute("error", "Status is linked to one or more assets and cannot be edited.");
            return "redirect:/statuses/";
        }

        if (!StringUtils.hasText(status.getStatusName())) {
            bind.rejectValue("statusName", "NotBlank", "Status name is required.");
        }

        if (bind.hasErrors()) {
            model.addAttribute("isEdit", true);
            model.addAttribute("inUse", inUse);
            model.addAttribute("pageTitle", "Edit Status");
            return "statuses/form";
        }

        try {
            existing.setStatusName(status.getStatusName().trim());
            assetStatusService.updateAssetStatus(existing);
            ra.addFlashAttribute("success", "Status updated successfully.");
            return "redirect:/statuses/";
        } catch (Exception e) {
            bind.rejectValue("statusName", "Duplicate", "Status name must be unique.");
            model.addAttribute("isEdit", true);
            model.addAttribute("inUse", inUse);
            model.addAttribute("pageTitle", "Edit Status");
            return "statuses/form";
        }
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id, RedirectAttributes ra) {
        AssetStatus s = assetStatusService.getAssetStatusById(id);
        if (s == null) {
            ra.addFlashAttribute("error", "Status not found.");
            return "redirect:/statuses/";
        }

        long usage = assetRepository.countByStatus_Id(id);
        if (usage > 0) {
            ra.addFlashAttribute("error", "Status is linked to one or more assets and cannot be deleted.");
            return "redirect:/statuses/";
        }

        assetStatusService.deleteAssetStatusById(id);
        ra.addFlashAttribute("success", "Status deleted successfully.");
        return "redirect:/statuses/";
    }

    @GetMapping("/search")
    public String search(@RequestParam("keyword") String keyword, Model model) {
        return list(keyword, model);
    }
}
