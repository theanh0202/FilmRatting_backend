package com.example.filmrating.modal.dto;

import com.example.filmrating.modal.entity.Role;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.Date;

@Data
public class AccountUpdateRequest {
    @NotNull(message = "Id không được để trống")
    @Positive(message = "Id phải lớn hơn 0")
    private int id;

    @NotBlank(message = "Username không được để trống")
    @Length(max = 50, message = "Username không được quá 50 ký tự")
    private String username;

    private Role role;

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
