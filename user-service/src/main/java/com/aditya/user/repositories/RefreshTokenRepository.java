package com.aditya.user.repositories;

import com.aditya.user.models.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken,Integer > {

    Optional<RefreshToken> findByRefreshToken(String refreshToken);
}

