package vn.edu.huflit.clothes.models;


public class UserRegisterDTO{
  private String email;
  private String nameCustomer;
  private String phoneNumber;
  private String password;
  private String address;

 public UserRegisterDTO(String email,String nameCustomer, String phone, String password, String address) {
  this.email = email;
  this.nameCustomer = nameCustomer;
  this.phoneNumber = phone;
  this.password = password;
  this.address = address;
 }

 public String getEmail() {
  return email;
 }

 public void setEmail(String email) {
  this.email = email;
 }

 public String getNameCustomer(){
  return nameCustomer;
 }

 public void setNameCustomer(String nameCustomer){
  this.nameCustomer = nameCustomer;
 }

 public String getPhoneNumber() {
  return phoneNumber;
 }

 public void setPhoneNumber(String phoneNumber) {
  this.phoneNumber = phoneNumber;
 }

 public String getPassword() {
  return password;
 }

 public void setPassword(String password) {
  this.password = password;
 }

 public String getAddress() {
  return address;
 }

 public void setAddress(String address) {
  this.address = address;
 }
}
