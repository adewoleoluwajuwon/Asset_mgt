package com.Jguides.spingboot.Model;

import jakarta.persistence.*;

@Entity
@Table(name = "warehouses")
public class Warehouse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "warehouse_id")
    private Integer id;

    @Column(name = "warehouse_name", nullable = false, unique = true)
    private String warehouse_name;

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getWarehouse_name() { return warehouse_name; }
    public void setWarehouse_name(String warehouse_name) { this.warehouse_name = warehouse_name; }
}
