package com.integrant.trainingtask.DTO;

public class ProductQuantity {
    public ProductQuantity(Integer id, Integer quantity) {
        this.id = id;
        this.quantity = quantity;
    }

    private Integer id;
    private Integer quantity;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
