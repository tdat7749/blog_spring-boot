package com.example.blog_springboot.modules.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

public class ChangePasswordDTO {

    @NotBlank(message = "Không được bỏ trống trường 'oldPassword'")
    @NotNull(message = "Không được thiếu trường 'oldPassword'")
    private String oldPassword;

    @NotBlank(message = "Không được bỏ trống trường 'newPassword'")
    @NotNull(message = "Không được thiếu trường 'newPassword'")
    @Length(max = 40,message = "Mật khẩu có độ dài tối đa là 40 ký tự")
    private String newPassword;


    @NotBlank(message = "Không được bỏ trống trường 'confirmPassword'")
    @NotNull(message = "Không được thiếu trường 'confirmPassword'")
    private String confirmPassword;

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

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
}
