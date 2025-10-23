package com.Jguides.spingboot.service;

import java.util.List;
import com.Jguides.spingboot.Model.Warehouse;

public interface WarehouseService {
    List<Warehouse> getAllWarehouses();

    Warehouse getWarehouseById(Long id);

    Warehouse saveWarehouse(Warehouse warehouse);

    void deleteWarehouse(Long id);
}
