package com.adil.eshop.services;

import com.adil.eshop.domain.User;
import com.adil.eshop.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public User createOrUpdate(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public Optional<User> findById(Long id) {
       return userRepository.findById(id);
    }

    public List<User> findAll() {
       return userRepository.findAll();
    }

    @Transactional
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    public boolean existById(Long id) {
        return userRepository.existsById(id);
    }
}
