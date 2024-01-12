package com.example.filmrating.repository;

import com.example.filmrating.modal.entity.ActiveUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ActiveUserRepository extends JpaRepository<ActiveUser, Integer> {

    Optional<ActiveUser> findFirstByUuid(String uuid);
}
