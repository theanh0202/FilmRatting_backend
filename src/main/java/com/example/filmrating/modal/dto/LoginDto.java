package com.example.filmrating.modal.dto;

import com.example.filmrating.modal.entity.Role;
import lombok.Data;

@Data
public class LoginDto {
    private int id;
    private String username;
    private Role role;
    private String fullName;
    private String avatar;

    private String userAgent; // Tên trình duyệt đang sử dụng
    private String token;
}
