package com.example.filmrating.controller;

import com.example.filmrating.modal.dto.AccountCreateRequest;
import com.example.filmrating.modal.dto.AccountSearchRequest;
import com.example.filmrating.modal.dto.AccountUpdateRequest;
import com.example.filmrating.modal.entity.Account;
import com.example.filmrating.modal.entity.AccountStatus;
import com.example.filmrating.modal.entity.ActiveUser;
import com.example.filmrating.repository.AccountRepository;
import com.example.filmrating.repository.ActiveUserRepository;
import com.example.filmrating.service.iml.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/account")
@CrossOrigin("*")
@Validated
public class AccountController {
    @Autowired
    private AccountService accountService;

    @Autowired
    private ActiveUserRepository activeUserRepository;

    @Autowired
    private AccountRepository accountRepository;

    @GetMapping("/get-all")
    @PreAuthorize(value = "hasAuthority('ADMIN')")
    public List<Account> getAll() {
        return accountService.getAll();
    }

    @PostMapping("/create")
    public Account create(@RequestBody @Valid AccountCreateRequest request){
        return accountService.create(request);
    }

    @GetMapping("/{id}")
    public Account getById(@PathVariable int id) {
        return accountService.getById(id);
    }

    @GetMapping("/active/{uuid}")
    public String activeUser(@PathVariable String uuid) {
        Optional<ActiveUser> activeUserOptional = activeUserRepository.findFirstByUuid(uuid);
        if (activeUserOptional.isEmpty()){
            return "Mã kích hoạt không tồn tại";
        }
        ActiveUser activeUser = activeUserOptional.get();
        Account account = activeUser.getAccount();
        account.setStatus(AccountStatus.ACTIVE);
                accountRepository.save(account);
        return "Tài khoản của bạn đã được kích hoạt";
    }

    @PutMapping("/update")
    public Account update(@RequestBody @Valid AccountUpdateRequest request){
        return accountService.update(request);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize(value = "hasAuthority('ADMIN')")
    public String delete(@PathVariable int id) {
        accountService.delete(id);
        return "Đã xóa thành công";
    }

    @PostMapping("/search")
    @PreAuthorize(value = "hasAuthority('ADMIN')")
    public Page<Account> search(@RequestBody AccountSearchRequest request) {
        return accountService.search(request);
    }
}
