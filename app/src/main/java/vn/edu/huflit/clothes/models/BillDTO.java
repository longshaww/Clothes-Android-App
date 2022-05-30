package vn.edu.huflit.clothes.models;

import java.util.List;

public class BillDTO {
    private String userID;
    private String nameCustomer;
    private String email;
    private String phoneNumber;
    private String address;
    private String paymentMethod;
    private List<Cart> listProduct;

    public BillDTO(String userID ,String nameCustomer, String email, String phoneNumber, String address, String paymentMethod, List<Cart> listProduct) {
        this.userID = userID;
        this.nameCustomer = nameCustomer;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.paymentMethod = paymentMethod;
        this.listProduct = listProduct;
    }

    public BillDTO(){

    }

    public String getNameCustomer() {
        return nameCustomer;
    }

    public void setNameCustomer(String nameCustomer) {
        this.nameCustomer = nameCustomer;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public List<Cart> getListProduct() {
        return listProduct;
    }

    public void setListProduct(List<Cart> listProduct) {
        this.listProduct = listProduct;
    }
}


