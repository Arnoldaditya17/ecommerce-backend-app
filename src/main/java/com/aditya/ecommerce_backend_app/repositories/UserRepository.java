package com.aditya.ecommerce_backend_app.repositories;

import com.aditya.ecommerce_backend_app.enums.UserRole;
import com.aditya.ecommerce_backend_app.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findFirstByEmail(String username);

    User findByRole(UserRole userRole);





}
