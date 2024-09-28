package com.aditya.user.repositories;


import com.aditya.user.enums.RoleEnum;
import com.aditya.user.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByName(RoleEnum name);
}
