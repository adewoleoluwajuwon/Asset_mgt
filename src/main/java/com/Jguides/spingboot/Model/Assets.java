package com.Jguides.spingboot.Model;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
//import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "assets")

public class Assets {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "description")
	public String description;
	
	@Column(name = "unit_cost")
	public BigDecimal unitCost;
	
	@Column(name = "quantity")
	public int quantity;
	
	@Column(name = "quantity_available")
	public int quantityAvailable;
	
	@Column(name = "total_cost", insertable = false, updatable = false) 
	public BigDecimal totalCost;
	
	@Column(name = "purchase_date")
	public LocalDate purchaseDate;
	
	 @ManyToMany
	 @JoinColumn(name = "warehouse_id", nullable = false)
	 private Warehouse warehouse;

//    @ManyToOne
//    @JoinColumn(name = "status_id",  = false)
//    private Status status;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public BigDecimal getUnitCost() {
		return unitCost;
	}

	public void setUnitCost(BigDecimal unitCost) {
		this.unitCost = unitCost;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public int getQuantityAvailable() {
		return quantityAvailable;
	}

	public void setQuantityAvailable(int quantityAvailable) {
		this.quantityAvailable = quantityAvailable;
	}

	public BigDecimal getTotalCost() {
		return totalCost;
	}

	public void setTotalCost(BigDecimal totalCost) {
		this.totalCost = totalCost;
	}

	public LocalDate getPurchaseDate() {
		return purchaseDate;
	}

	public void setPurchaseDate(LocalDate purchaseDate) {
		this.purchaseDate = purchaseDate;
	}

	public Warehouse getWarehouse() {
		return warehouse;
	}

	public void setWarehouse(Warehouse warehouse) {
		this.warehouse = warehouse;
	}

//	public Status getStatus() {
//		return status;
//	}
//
//	public void setStatus(Status status) {
//		this.status = status;
//	}
    
}
