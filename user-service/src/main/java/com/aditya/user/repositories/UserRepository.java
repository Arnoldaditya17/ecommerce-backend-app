package com.aditya.user.repositories;


import com.aditya.user.enums.UserRole;
import com.aditya.user.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findFirstByEmail(String username);

    User findByRole(UserRole userRole);





}
