package com.Jguides.spingboot.service;

import java.util.List;

import com.Jguides.spingboot.Model.AssetStatus;

public interface AssetStatusService {
	List<AssetStatus> getAllAssetStatus();
	
	AssetStatus getAssetStatusById(Long id);
	
	AssetStatus saveAssetStatus(AssetStatus assetStatus);
	
	void deleteAssetStatus(Long id);
	
	List<AssetStatus> searchAssetStatus(String keyword);

    AssetStatus updateAssetStatus(AssetStatus status);

    void deleteAssetStatusById(Long id);
}
