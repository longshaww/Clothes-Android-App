package vn.edu.huflit.clothes.models;

public class Customer {
    private String _id;
    private String nameCustomer;
    private String address;
    private String email;
    private String phoneNumber;
    private Boolean isRegister;
    private String userID;

    public Customer(String _id, String nameCustomer, String address, String email, String phoneNumber, Boolean isRegister, String userID) {
        this._id = _id;
        this.nameCustomer = nameCustomer;
        this.address = address;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.isRegister = isRegister;
        this.userID = userID;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getNameCustomer() {
        return nameCustomer;
    }

    public void setNameCustomer(String nameCustomer) {
        this.nameCustomer = nameCustomer;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public Boolean getRegister() {
        return isRegister;
    }

    public void setRegister(Boolean register) {
        isRegister = register;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }
}
