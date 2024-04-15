package vn.melowyeti.spring.spring_ecommerce_project.web;

import jakarta.validation.constraints.*;

public class RegisterUser {
    @NotNull(message = "Thong tin bat buoc")
    @Size(min = 1, message = "Do dai toi thieu la 1")
    private String username;
    @NotNull
    @Size(min = 8, message = "Do dai toi thieu la 8")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[@#$%^&+=!])(?=\\S+$).*$",
        message = "Mat khau phai chua it nhat 1 chu so va 1 ky tu dat biet")
    private String password;
    private String email;
    //@NotBlank(message = "Thong tin bat buoc")
//    private String firstName;
//    //@NotBlank(message = "Thong tin bat buoc")
//    private String lastName;
//    @NotBlank(message = "Thong tin bat buoc")
//    @Email(message = "Email khong hop le")
//    private String email;

    public RegisterUser() {
    }

    public RegisterUser(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
