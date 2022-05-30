package vn.edu.huflit.clothes.models;

public class UpdateAddressPhoneNumberDTO {
    public String userId;
    public String phoneNumber;
    public String address;

    public UpdateAddressPhoneNumberDTO(String userId, String address, String phoneNumber) {
        this.userId = userId;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }
}
