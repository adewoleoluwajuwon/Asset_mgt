package com.Jguides.spingboot.service;

import java.util.List;

import com.Jguides.spingboot.Model.Assets;

public interface AssetService {
	List<Assets> getAllAssets();
	Assets getAssetById(Long id);
	Assets saveAsset(Assets asset);
	void deleteAsset(Long id);
	List<Assets> searchAssets(String keyword);
}
