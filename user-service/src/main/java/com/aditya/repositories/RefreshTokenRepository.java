package com.aditya.repositories;


import com.aditya.models.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
   Optional<RefreshToken> findByToken(String token);

}
