package com.example.filmrating.exception;

public enum ErrorResponseEnum {
    ACCOUNT_NOT_FOUND(404, "Tài khoản không tồn tại"),
    USERNAME_EXISTED(404, "Username đã tồn tại"),
    USERNAME_NOT_EXISTED(404, "Username không tồn tại"),
    WRONG_PASSWORD(401, "Sai mật khẩu"),
    ACCOUNT_INACTIVE(401, "Tài khoản chưa được kích hoạt"),

    FILM_NOT_FOUND(404,"Phim không tồn tại" ),

    REVIEW_NOT_FOUND(404, "Review không tồn tại");

    public final int status;
    public final String message;
    ErrorResponseEnum(int status, String message) {
        this.status = status;
        this.message = message;
    }
}
