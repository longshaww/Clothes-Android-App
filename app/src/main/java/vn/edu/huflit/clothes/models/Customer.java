package vn.edu.huflit.clothes.models;

public class Customer {
    private String nameCustomer;
    private String address;
    private String email;
    private String phoneNumber;
    private Boolean isRegister;

    public Customer(String nameCustomer, String address, String email, String phoneNumber, Boolean isRegister) {
        this.nameCustomer = nameCustomer;
        this.address = address;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.isRegister = isRegister;
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

    @Override
    public String toString() {
        return "Customer{" +
                "nameCustomer='" + nameCustomer + '\'' +
                ", address='" + address + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", isRegister=" + isRegister +
                '}';
    }
}
