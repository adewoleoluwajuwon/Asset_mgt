package com.Jguides.spingboot.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Jguides.spingboot.Model.AssetStatus;

@Repository

public interface AssetStatusRepository extends JpaRepository<AssetStatus, Long>{

	List<AssetStatus> findByDescriptionContainingIgnoreCase(String keyword);

}
