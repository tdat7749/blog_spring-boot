package com.example.blog_springboot.modules.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

public class ForgotPasswordDTO {
    @NotBlank(message = "Không được bỏ trống trường 'newPassword'")
    @NotNull(message = "Không được thiếu trường 'newPassword'")
    @Length(max = 40,message = "Mật khẩu có độ dài tối đa là 40 ký tự")
    private String newPassword;


    @NotBlank(message = "Không được bỏ trống trường 'confirmPassword'")
    @NotNull(message = "Không được thiếu trường 'confirmPassword'")
    private String confirmPassword;


    @NotBlank(message = "Không được bỏ trống trường 'code'")
    @NotNull(message = "Không được thiếu trường 'code'")
    private String code;

    @NotBlank(message = "Không được bỏ trống trường 'email'")
    @NotNull(message = "Không được thiếu trường 'email'")
    private String email;

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
