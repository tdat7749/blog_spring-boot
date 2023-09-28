package com.example.blog_springboot.modules.authenticate.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

public class RegisterDTO {

    @NotBlank(message = "Không được bỏ trống trường 'userName'")
    @NotNull(message = "Không được thiếu trường 'userName'")
    @Length(max = 50,message = "Tài khoản có độ dài tối đa là 50 ký tự")
    private String userName;

    @NotBlank(message = "Không được bỏ trống trường 'password'")
    @NotNull(message = "Không được thiếu trường 'password'")
    @Length(max = 40,message = "Mật khẩu có độ dài tối đa là 40 ký tự")
    private String password;

    @NotBlank(message = "Không được bỏ trống trường 'confirmPassword'")
    @NotNull(message = "Không được thiếu trường 'confirmPassword'")
    private String confirmPassword;

    @NotBlank(message = "Không được bỏ trống trường 'email'")
    @NotNull(message = "Không được thiếu trường 'email'")
    @Email(message = "Email không đúng định dạng")
    private String email;

    @NotBlank(message = "Không được bỏ trống trường 'firstName'")
    @NotNull(message = "Không được thiếu trường 'firstName'")
    @Length(max = 60,message = "Họ có độ dài tối đa là 70 ký tự")
    private String firstName;

    @NotBlank(message = "Không được bỏ trống trường 'lastName'")
    @NotNull(message = "Không được thiếu trường 'lastName'")
    @Length(max = 70,message = "Tên có độ dài tối đa là 70 ký tự")
    private String lastName;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
