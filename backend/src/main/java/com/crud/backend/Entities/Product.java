package com.crud.backend.Entities;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "Productos")
public class Product {
    //e-commerce product entity

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;
    @Column
    private String description;
    @Column
    private Float price;
    @Column
    private Integer stock;
    @Column
    private String category;
    @Column
    private String publicationDate;

    // Constructor vacío
    public Product() {}

    // Constructor SIN ID (para crear nuevos productos)
    public Product(String name, String description, Float price,
                   Integer stock, String category, String publicationDate) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.stock = stock;
        this.category = category;
        this.publicationDate = publicationDate;
    }

    // Constructor con todos los parámetros
    public Product(Long id, String name, String description, Float price,
                   Integer stock, String category, String publicationDate) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.stock = stock;
        this.category = category;
        this.publicationDate = publicationDate;
    }

    // Getters
    public Long getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getDescription() {
        return description;
    }
    public Float getPrice() {
        return price;
    }
    public Integer getStock() {
        return stock;
    }
    public String getCategory() {
        return category;
    }
    public String getPublicationDate() {
        return publicationDate;
    }

    // Setters
    public void setId(Long id) {
        this.id = id;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public void setPrice(Float price) {
        this.price = price;
    }
    public void setStock(Integer stock) {
        this.stock = stock;
    }
    public void setCategory(String category) {
        this.category = category;
    }
    public void setPublicationDate(String publicationDate) {
        this.publicationDate = publicationDate;
    }

}
