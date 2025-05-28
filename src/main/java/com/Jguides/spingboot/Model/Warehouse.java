package com.Jguides.spingboot.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "warehouses")

public class Warehouse {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "warehouse_id")
	private Integer id;
	
	@Column(name = "warehouse_name")
	public String warehouse_name;
	
	//Getter and Setters
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getWarehouse_name() {
		return warehouse_name;
	}

	public void setWarehouse_name(String warehouse_name) {
		this.warehouse_name = warehouse_name;
	}	
	
	
}
