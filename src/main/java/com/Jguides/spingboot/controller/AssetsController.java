package com.Jguides.spingboot.controller;

import com.Jguides.spingboot.Model.Assets;
import com.Jguides.spingboot.Model.AssetStatus;
import com.Jguides.spingboot.Model.Warehouse;
import com.Jguides.spingboot.service.AssetService;
import com.Jguides.spingboot.service.AssetStatusService;
import com.Jguides.spingboot.service.WarehouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/assets")
public class AssetsController {

    @Autowired private AssetService assetService;
    @Autowired private WarehouseService warehouseService;
    @Autowired private AssetStatusService assetStatusService;

    // ====== LIST (any authenticated user) ======
    @GetMapping("/")
    public String listAssets(@RequestParam(value = "keyword", required = false) String keyword, Model model) {
        List<Assets> listAssets = (keyword == null || keyword.isBlank())
                ? assetService.getAllAssets()
                : assetService.searchAssets(keyword.trim()); // <-- use your interface method
        model.addAttribute("assets", listAssets);
        model.addAttribute("keyword", keyword);
        return "index";
    }

    // ====== NEW (managers only) ======
    @PreAuthorize("hasAnyRole('ADMIN_MANAGER','STORE_MANAGER')")
    @GetMapping("/new")
    public String showNewAssetForm(Model model) {
        Assets asset = new Assets();
        asset.setWarehouse(new Warehouse());     // for binding warehouse.id
        asset.setStatus(new AssetStatus());      // for binding status.id

        model.addAttribute("asset", asset);
        model.addAttribute("warehouses", warehouseService.getAllWarehouses());
        model.addAttribute("statuses", assetStatusService.getAllAssetStatus());
        return "new_asset";
    }

    // ====== SAVE (managers only) ======
    // ====== SAVE (managers only) ======
    @PreAuthorize("hasAnyRole('ADMIN_MANAGER','STORE_MANAGER')")
    @PostMapping("/save")
    public String saveAsset(@ModelAttribute("asset") Assets asset) {
        Long statusId = (asset.getStatus() != null ? asset.getStatus().getId() : null);     // Long is fine for status
        Integer whId  = (asset.getWarehouse() != null ? asset.getWarehouse().getId() : null); // Integer for warehouse

        if (statusId == null || whId == null) {
            throw new IllegalArgumentException("Status and Warehouse are required");
        }

        // resolve entities using correct ID types
        asset.setStatus(assetStatusService.getAssetStatusById(statusId)); // expects Long
        asset.setWarehouse(warehouseService.getWarehouseById(whId));      // expects Integer

        assetService.saveAsset(asset);
        return "redirect:/assets/";
    }

    // ====== EDIT (managers only) ======
    @PreAuthorize("hasAnyRole('ADMIN_MANAGER','STORE_MANAGER')")
    @GetMapping("/edit/{id}")
    public String editAsset(@PathVariable("id") Long id, Model model) {
        Assets asset = assetService.getAssetById(id);
        if (asset.getWarehouse() == null) asset.setWarehouse(new Warehouse());
        if (asset.getStatus() == null) asset.setStatus(new AssetStatus());

        model.addAttribute("asset", asset);
        model.addAttribute("warehouses", warehouseService.getAllWarehouses());
        model.addAttribute("statuses", assetStatusService.getAllAssetStatus());
        return "new_asset";
    }

    // ====== DELETE (managers only) ======
    @PreAuthorize("hasAnyRole('ADMIN_MANAGER','STORE_MANAGER')")
    @GetMapping("/delete/{id}")
    public String deleteAsset(@PathVariable("id") Long id) {
        assetService.deleteAsset(id);
        return "redirect:/assets/";
    }
}
