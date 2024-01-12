package com.example.filmrating.service.iml;

import com.example.filmrating.exception.AppException;
import com.example.filmrating.exception.ErrorResponseEnum;
import com.example.filmrating.modal.dto.AccountCreateRequest;
import com.example.filmrating.modal.dto.AccountSearchRequest;
import com.example.filmrating.modal.dto.AccountUpdateRequest;
import com.example.filmrating.modal.entity.Account;
import com.example.filmrating.modal.entity.AccountStatus;
import com.example.filmrating.modal.entity.ActiveUser;
import com.example.filmrating.modal.entity.Role;
import com.example.filmrating.repository.AccountRepository;
import com.example.filmrating.repository.ActiveUserRepository;
import com.example.filmrating.repository.specification.AccountSpecification;
import com.example.filmrating.service.IAccountService;
import com.example.filmrating.service.IMailSenderService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class AccountService implements IAccountService, UserDetailsService {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private IMailSenderService mailSenderService;
    @Autowired
    private ActiveUserRepository activeUserRepository;

    @Override
    public List<Account> getAll() {
        return accountRepository.findAll();
    }

    @Override
    public Account create(AccountCreateRequest request) {
        Optional<Account> optionalAccount = accountRepository.findByUsername(request.getUsername());
        if (optionalAccount.isPresent()) {
            throw new AppException(ErrorResponseEnum.USERNAME_EXISTED);
        }
        Account account = new Account();
        BeanUtils.copyProperties(request, account);

        String passEncoder = passwordEncoder.encode(request.getPassword());
        account.setPassword(passEncoder);

        account.setRole(Role.USER);

        account.setStatus(AccountStatus.PENDING);
        account = accountRepository.save(account);

        ActiveUser activeUser = new ActiveUser();
        String uuid = UUID.randomUUID().toString();
        activeUser.setUuid(uuid);
        activeUser.setAccount(account);
        activeUserRepository.save(activeUser);
        String activeUrl = "http://localhost:8080/api/v1/account/active/" + uuid;
        String text = "Bạn vừa tạo tài khoản có usename là: " + request.getUsername()
                + ". Để kích hoạt tài khoản "
                + "<a href=\""+activeUrl+"\" target=\"_blank\">Click </a>";
        mailSenderService.sendMessageWithHtml(request.getEmail(), "Thông báo tới tài khoản!", text);
        return account;
    }



    @Override
    public Account getById(int id) {
        Optional<Account> accountOptional = accountRepository.findById(id);
        if (accountOptional.isPresent()) {
            return accountOptional.get();
        } else {
            throw new AppException(ErrorResponseEnum.ACCOUNT_NOT_FOUND);
        }
    }

    @Override
    public Account update(AccountUpdateRequest request) {
        Account account = getById(request.getId());
        if (account != null) {
            BeanUtils.copyProperties(request, account);
            return accountRepository.save(account);
        } else {
            throw new AppException(ErrorResponseEnum.ACCOUNT_NOT_FOUND);
        }
    }

    @Override
    public void delete(int id) {
        Optional<Account> accountOptional = accountRepository.findById(id);
        if (accountOptional.isEmpty()) {
            throw new AppException(ErrorResponseEnum.ACCOUNT_NOT_FOUND);
        } else {
            accountRepository.deleteById(id);
        }
    }

    @Override
    public Page<Account> search(AccountSearchRequest request) {
        PageRequest pageRequest = null;
        if ("DESC".equals(request.getSortType())) {
            pageRequest = PageRequest.of(request.getPage() - 1, request.getSize(), Sort.by(request.getSortField()).descending());
        } else {
            pageRequest = PageRequest.of(request.getPage() - 1, request.getSize(), Sort.by(request.getSortField()).descending());
        }
        Specification<Account> condition = AccountSpecification.buildCondition(request);
        return accountRepository.findAll(condition, pageRequest);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Account> accountOptional = accountRepository.findByUsername(username);
        if (accountOptional.isEmpty()) {
            throw new UsernameNotFoundException("Username không tồn tại");
        }
        Account account = accountOptional.get();
        // Nếu có tồn tại -> tạo ra đối tượng Userdetails từ Account
        /**
         * username
         * account.getPassword(): password đã được mã hóa
         * authorities: List quyền của user
         */
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(account.getRole());
        return new User(username, account.getPassword(), authorities);
    }
}