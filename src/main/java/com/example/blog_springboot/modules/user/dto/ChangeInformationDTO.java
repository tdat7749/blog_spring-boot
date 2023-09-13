package com.example.blog_springboot.modules.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

public class ChangeInformationDTO {
    @NotBlank(message = "Không được bỏ trống trường 'firstName'")
    @NotNull(message = "Không được thiếu trường 'firstName'")
    @Length(max = 60,message = "Họ có độ dài tối đa là 60 ký tự")
    private String firstName;

    @NotBlank(message = "Không được bỏ trống trường 'lastName'")
    @NotNull(message = "Không được thiếu trường 'lastName'")
    @Length(max = 70,message = "Họ có độ dài tối đa là 70 ký tự")
    private String lastName;

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
