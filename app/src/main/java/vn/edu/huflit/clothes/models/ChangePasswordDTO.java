package vn.edu.huflit.clothes.models;

public class ChangePasswordDTO {
    public String userId;
    public String currentPassword;
    public String password;

    public ChangePasswordDTO(String userId, String currentPassword, String password) {
        this.userId = userId;
        this.currentPassword = currentPassword;
        this.password = password;
    }
}
