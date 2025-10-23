package com.Jguides.spingboot.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Jguides.spingboot.Model.AssetStatus;
import com.Jguides.spingboot.repository.AssetStatusRepository;

@Service
public class AssetStatusImpl implements AssetStatusService {

    @Autowired
    private AssetStatusRepository assetStatusRepository;

    @Override
    public List<AssetStatus> getAllAssetStatus() {
        return assetStatusRepository.findAll();
    }

    @Override
    public AssetStatus getAssetStatusById(Long id) {
        return assetStatusRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteAssetStatus(Long id) {
        assetStatusRepository.deleteById(id);
    }

    @Override
    public List<AssetStatus> searchAssetStatus(String keyword) {

        return assetStatusRepository.findByStatusNameContainingIgnoreCase(keyword);
    }

    @Override
    public AssetStatus saveAssetStatus(AssetStatus assetStatus) {
        return assetStatusRepository.save(assetStatus);
    }
}
