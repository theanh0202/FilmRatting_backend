package com.example.filmrating.service;

import com.example.filmrating.modal.dto.AccountCreateRequest;
import com.example.filmrating.modal.dto.AccountSearchRequest;
import com.example.filmrating.modal.dto.AccountUpdateRequest;
import com.example.filmrating.modal.entity.Account;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IAccountService {
    List<Account> getAll();

    Account create(AccountCreateRequest request);

    Account getById(int id);

    Account update(AccountUpdateRequest request);

    void delete(int id);

    Page<Account> search(AccountSearchRequest request);
}
