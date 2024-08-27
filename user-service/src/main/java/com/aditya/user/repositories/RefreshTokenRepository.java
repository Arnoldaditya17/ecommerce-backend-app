package com.aditya.user.repositories;


import com.aditya.user.models.RefreshTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshTokenEntity, Long> {
   Optional<RefreshTokenEntity> findByToken(String token);

}
