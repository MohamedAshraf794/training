package com.integrant.trainingtask.DTO;

import java.util.Objects;

public class ProductsOrderInfo {
    private Integer status;
    private Double orderCash;

    public ProductsOrderInfo(Integer status, Double orderCash) {
        this.status = status;
        this.orderCash = orderCash;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Double getOrderCash() {
        return orderCash;
    }

    public void setOrderCash(Double orderCash) {
        this.orderCash = orderCash;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductsOrderInfo that = (ProductsOrderInfo) o;
        return Objects.equals(status, that.status) && Objects.equals(orderCash, that.orderCash);
    }
}
