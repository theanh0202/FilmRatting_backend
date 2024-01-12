package com.example.filmrating.modal.dto;

import com.example.filmrating.modal.entity.Role;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.util.Date;

@Data
public class AccountCreateRequest {
    @NotBlank(message = "Username không được để trống")
    @Length(max = 50, message = "Username không được quá 50 ký tự")
    private String username;

    @NotBlank(message = "Mật khẩu không được để trống")
    @Length(max = 50, message = "Mật khẩu không được quá 50 ký tự")
    private String password;

    @Length(max = 50, message = "Tên không được quá 50 ký tự")
    private String fullName;

    @NotBlank(message = "Email không được để trống")
    @Length(max = 50, message = "Email không được quá 50 ký tự")
    private String email;

    private String avatar;

    private Date dateOfBirth;
}
