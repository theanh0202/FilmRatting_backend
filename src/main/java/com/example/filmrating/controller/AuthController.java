package com.example.filmrating.controller;

import com.example.filmrating.exception.AppException;
import com.example.filmrating.exception.ErrorResponseEnum;
import com.example.filmrating.modal.dto.LoginDto;
import com.example.filmrating.modal.dto.LoginRequest;
import com.example.filmrating.modal.entity.Account;
import com.example.filmrating.modal.entity.AccountStatus;
import com.example.filmrating.repository.AccountRepository;
import com.example.filmrating.utils.JWTTokenUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/auth")
@CrossOrigin("*")
public class AuthController {
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    HttpServletRequest httpServletRequest;

    @Autowired
    private JWTTokenUtils jwtTokenUtils;

    /**
     * Hàm login: Cần set authenticated() cho api này
     * @param principal: Đối tượng được sinh ra khi đã xác thực được người dùng
     * @return
     */
    @GetMapping("/login-basic-v1")
    public LoginDto loginV1(Principal principal){
        String username = principal.getName();
        // Tìm ra được đối tượng Account từ username
        Optional<Account> accountOptional = accountRepository.findByUsername(username);
        if (accountOptional.isEmpty()) {
            throw new AppException(ErrorResponseEnum.ACCOUNT_NOT_FOUND);
        }
        Account account = accountOptional.get();
        LoginDto loginDto = new LoginDto();
        BeanUtils.copyProperties(account, loginDto);
        return loginDto;
    }

    /**
     * Hàm login: Cách này cần permitAll() với api này để xử lý dữ liệu
     * @param username
     * @param password
     * @return
     */
    @GetMapping("/login-basic-v2")
    public LoginDto loginV2(@RequestParam String username, @RequestParam String password) {
        Optional<Account> accountOptional = accountRepository.findByUsername(username);
        if (accountOptional.isEmpty()) {
            throw new AppException(ErrorResponseEnum.USERNAME_NOT_EXISTED);
        }
        Account account = accountOptional.get();
        boolean checkPassword = passwordEncoder.matches(password, account.getPassword());
        if (!checkPassword) {
            throw new AppException(ErrorResponseEnum.WRONG_PASSWORD);
        }
        LoginDto loginDto = new LoginDto();
        BeanUtils.copyProperties(account, loginDto);
        return loginDto;
    }

    /**
     * Hàm login JWT: Cách này cần permitAll() với api này để xử lý dữ liệu
     * @param request: Đối tượng gồm username và password
     * @return
     */
    @PostMapping("/login-jwt")
    public LoginDto loginJWT(@RequestBody LoginRequest request) {
        Optional<Account> accountOptional = accountRepository.findByUsername(request.getUsername());
        if (accountOptional.isEmpty()) {
            throw new AppException(ErrorResponseEnum.USERNAME_NOT_EXISTED);
        }
        Account account = accountOptional.get();
        boolean checkPassword = passwordEncoder.matches(request.getPassword(), account.getPassword());
        if (!checkPassword) {
            throw new AppException(ErrorResponseEnum.WRONG_PASSWORD);
        }
        if (account.getStatus() != AccountStatus.ACTIVE) {
            throw new AppException(ErrorResponseEnum.ACCOUNT_INACTIVE);
        }
        // Login
        LoginDto loginDto = new LoginDto();
        BeanUtils.copyProperties(account, loginDto);
        loginDto.setUserAgent(httpServletRequest.getHeader("User-Agent"));
        String token = jwtTokenUtils.createAccessToken(loginDto);
        loginDto.setToken(token);
        return loginDto;
    }

}
