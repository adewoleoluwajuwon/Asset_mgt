package com.Jguides.spingboot.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.Jguides.spingboot.Model.Assets;

@Repository
public interface AssetRepository extends JpaRepository<Assets, Long>{

	List<Assets> findByDescriptionContainingIgnoreCase(String keyword);

    long countByStatus_Id(Long statusId);

}
