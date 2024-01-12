package com.example.filmrating.modal.dto;

import lombok.Data;

@Data
public class AccountSearchRequest extends BaseRequest{
    private String username;
    private String fullName;
    private String email;
}
