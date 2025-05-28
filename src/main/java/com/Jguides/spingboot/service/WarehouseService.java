package com.Jguides.spingboot.service;

import java.util.List;

import com.Jguides.spingboot.Model.Warehouse;

public interface WarehouseService {
	List<Warehouse> getAllWarehouse();
	
	Warehouse getWarehouseById(Integer id);
	Warehouse saveWarehouse(Warehouse warehouse);
	void deleteWarehouse(Integer id);
	
	List<Warehouse> searchWarehouse(String keyword);
}
