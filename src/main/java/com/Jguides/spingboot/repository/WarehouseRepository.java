package com.Jguides.spingboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.Jguides.spingboot.Model.Warehouse;

public interface WarehouseRepository extends JpaRepository<Warehouse, Long> {
}
