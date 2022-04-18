package com.integrant.trainingtask.enums;

public enum ProductStatus {
    DEACTIVATED(0),
    ACTIVATED(1);

    private int status;
    private ProductStatus (int status){
        this.status=status;
    }
    public int getStatus() {
        return status;
    }
}
