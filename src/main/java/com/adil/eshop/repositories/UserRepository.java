package com.adil.eshop.repositories;

import com.adil.eshop.domain.User;
import com.adil.eshop.dto.UserDTO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findUserByLogin(String login);
}
