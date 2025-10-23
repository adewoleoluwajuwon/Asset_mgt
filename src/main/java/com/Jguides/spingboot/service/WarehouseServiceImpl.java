package com.Jguides.spingboot.service;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Jguides.spingboot.Model.Warehouse;
import com.Jguides.spingboot.repository.WarehouseRepository;

@Service
@RequiredArgsConstructor
public class WarehouseServiceImpl implements WarehouseService {

    private final WarehouseRepository repo;

    @Override
    public List<Warehouse> getAllWarehouses() {
        return repo.findAll();
    }

    @Override
    public Warehouse getWarehouseById(Integer id) {
        return repo.findById(id).orElse(null);
    }

    @Override
    public Warehouse saveWarehouse(Warehouse warehouse) {
        return repo.save(warehouse);
    }

    @Override
    public void deleteWarehouse(Integer id) {
        repo.deleteById(id);
    }
}
