package com.aditya.tech.repositories;

import com.aditya.tech.enums.UserRole;
import com.aditya.tech.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findFirstByEmail(String username);

    User findByRole(UserRole userRole);





}
