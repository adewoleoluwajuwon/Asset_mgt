package com.Jguides.spingboot.controller;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.*;

import com.Jguides.spingboot.Model.Assets;
import com.Jguides.spingboot.service.AssetService;


@Controller


public class AssetsController {
	@Autowired
	private AssetService assetService;
	
	@GetMapping("/")
	public String showAssetsPage(Model model, @RequestParam(value = "keyword", required = false) String keyword) {
		List<Assets> listAssets;
		if(keyword != null && !keyword.isEmpty()) {
			listAssets = assetService.searchAssets(keyword);
			model.addAttribute("keyword", keyword);
		}else {
			listAssets = assetService.getAllAssets();
		}
		model.addAttribute("assets", listAssets);
		return "index";
	}
	
	//Form to add new asset
	@GetMapping("/showNewAssetForm")
	public String showNewAssetForm(Model model){
		Assets assets = new Assets();
		model.addAttribute("assets", assets);
		return "index";
	}
	
	//Save new or updated asset
	@PostMapping("/saveAsset")
	public String saveAsset(@ModelAttribute("asset") Assets asset) {
		assetService.saveAsset(asset);
		return "redirect:/";
	}
	
	@GetMapping("/showFormForUpdateAsset/{id}")
	public String showFormForUpdateAsset(@PathVariable(value = "id") Long id, Model model) {
		Assets asset = assetService.getAssetById(id);
		model.addAttribute("asset", asset);
		
		return "update_asset";
	}
	
	@GetMapping("/deleteAsset/{id}")
	public String deleteAsset(@PathVariable(value = "id") Long id) {
		assetService.getAssetById(id);
		return "redirect:/"; 	
	}	
}
