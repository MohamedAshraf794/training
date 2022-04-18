package com.integrant.trainingtask.enums;

public enum OrderStatus {
    CREATED(0),
    DELIVERD(1);
    private int status;
    private OrderStatus (int status){
        this.status=status;
    }
    public int getStatus() {
        return status;
    }
}
