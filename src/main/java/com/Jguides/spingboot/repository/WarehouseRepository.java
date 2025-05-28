package com.Jguides.spingboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Jguides.spingboot.Model.Warehouse;

@Repository
public interface WarehouseRepository extends JpaRepository<Warehouse, Integer> {

}
