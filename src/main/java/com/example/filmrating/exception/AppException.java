package com.example.filmrating.exception;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@JsonIgnoreProperties({"stackTrace", "cause", "suppressed", "localizedMessage"})
public class AppException extends RuntimeException{
    private Instant timestamp; // Thời gian xảy ra lỗi
    private int status; // Mã lỗi
    private String message; // Nguyên nhân xảy ra lỗi
    private String path; // Api xảy ra lỗi

    public AppException(int status, String error) {
        this.status = status;
        this.message = error;
        this.timestamp = Instant.now();
    }

    public AppException(int status, String error, String path) {
        this.status = status;
        this.message = error;
        this.path = path;
        this.timestamp = Instant.now();
    }

    public AppException(ErrorResponseEnum responseEnum) {
        this.status = responseEnum.status;
        this.message = responseEnum.message;
        this.timestamp = Instant.now();
    }
}