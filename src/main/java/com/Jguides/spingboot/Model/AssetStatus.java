package com.Jguides.spingboot.Model;

import jakarta.persistence.*;

@Entity
@Table(name = "statuses")
public class AssetStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "status_id")
    private Long id;

    @Column(name = "status_name", unique = true, nullable = false)
    private String statusName;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getStatusName() { return statusName; }
    public void setStatusName(String statusName) { this.statusName = statusName; }
}
