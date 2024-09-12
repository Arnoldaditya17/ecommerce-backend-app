package com.aditya.user.repositories;


import com.aditya.user.enums.UserRole;
import com.aditya.user.models.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findFirstByEmail(String username);

    UserEntity findByRole(UserRole userRole);





}
