package com.Jguides.spingboot.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
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

    @Override
    public AssetStatus updateAssetStatus(AssetStatus status) {
        // JPA save updates when the entity has an ID
        return assetStatusRepository.save(status);
    }

    public void deleteAssetStatusById(Long id) {
        // optional: existence check
        if (assetStatusRepository.existsById(id)) {
            try {
                assetStatusRepository.deleteById(id);
            } catch (DataIntegrityViolationException ex) {
                // If there’s a DB FK preventing delete, rethrow a clear message
                throw ex; // controller already blocks when "in use"; this is just a safety net
            }
        }
    }
}
