package com.integrant.trainingtask.enums;

public enum ReserveProductsStatus {
    SUCCESSFUL_RESERVATION(10),
    FAILED_PRODUCTS_NOT_ACTIVE(20),
    FAILED_NOT_ENOUGH_STOCK(30);

    private int status;
    private ReserveProductsStatus (int status){
        this.status=status;
    }
    public int getStatus() {
        return status;
    }

}
