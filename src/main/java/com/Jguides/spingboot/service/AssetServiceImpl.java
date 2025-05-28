package com.Jguides.spingboot.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Jguides.spingboot.Model.Assets;
import com.Jguides.spingboot.repository.AssetRepository;

@Service
public class AssetServiceImpl implements AssetService{
	@Autowired
	private AssetRepository assetRepository;
	
	@Override
	public List<Assets> getAllAssets(){
		return assetRepository.findAll();
	}
	
	@Override
	public Assets getAssetById(Long id) {
		return assetRepository.findById(id).orElse(null);
	}
	
	@Override
	public Assets saveAsset(Assets asset) {
		return assetRepository.save(asset);
	}
	
	@Override
	public void deleteAsset(Long id) {
		assetRepository.deleteById(id);
	}
	
	@Override
	public List<Assets> searchAssets(String keyword){
		return assetRepository.findByDescriptionContainingIgnoreCase(keyword);
	}
}
