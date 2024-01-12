package com.example.filmrating.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppExceptionDto {
    private Instant timestamp; // Thời gian xảy ra lỗi
    private int status; // Mã lỗi
    private String message; // Nguyên nhân xảy ra lỗi
    private String path; // Api xảy ra lỗi

    public AppExceptionDto(int status, String error) {
        this.status = status;
        this.message = error;
        this.timestamp = Instant.now();
    }

    public AppExceptionDto(String error, int status, String path) {
        this.message = error;
        this.status = status;
        this.path = path;
        this.timestamp = Instant.now();
    }

    public AppExceptionDto(ErrorResponseEnum responseEnum) {
        this.status = responseEnum.status;
        this.message = responseEnum.message;
        this.timestamp = Instant.now();
    }
}
