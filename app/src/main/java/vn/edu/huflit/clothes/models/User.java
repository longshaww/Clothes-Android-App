package vn.edu.huflit.clothes.models;

import java.util.Date;

public class User {
    public String _id;
    public String email;
    public String password;
    public Date dateOfBirth;
    public Boolean gender;
    public String avatar;
    public Customer customer;
    public Boolean isAdmin;

    public User(String _id, String email, String password, Date dateOfBirth, Boolean gender, String avatar, Customer customer, Boolean isAdmin) {
        this.email = email;
        this.password = password;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.avatar = avatar;
        this.customer = customer;
        this.isAdmin = isAdmin;
        this._id = _id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Boolean getGender() {
        return gender;
    }

    public void setGender(Boolean gender) {
        this.gender = gender;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Boolean getAdmin() {
        return isAdmin;
    }

    public void setAdmin(Boolean admin) {
        isAdmin = admin;
    }

    public String getName() {
        return customer.getNameCustomer();
    }

    public String getAddress() {
        return customer.getAddress();
    }

    public String getPhoneNumber() {
        return customer.getPhoneNumber();
    }

    public String getID() {
        return _id;
    }
}

