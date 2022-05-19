package vn.edu.huflit.clothes.models;

import java.util.Date;
import java.util.List;

public class Bill {
    private String _id;
    private String customerID;
    private List<Cart> listProduct;
    private String paymentMethod;
    private Boolean status;
    private Date createdAt;
    private Date updatedAt;

    public Bill(String _id, String customerID, List<Cart> listProduct, String paymentMethod, Boolean status, Date createdAt, Date updatedAt) {
        this._id = _id;
        this.customerID = customerID;
        this.listProduct = listProduct;
        this.paymentMethod = paymentMethod;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getCustomerID() {
        return customerID;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }

    public List<Cart> getListProduct() {
        return listProduct;
    }

    public void setListProduct(List<Cart> listProduct) {
        this.listProduct = listProduct;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "Bill{" +
                "_id='" + _id + '\'' +
                ", customerID='" + customerID + '\'' +
                ", listProduct=" + listProduct +
                ", paymentMethod='" + paymentMethod + '\'' +
                ", status=" + status +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
