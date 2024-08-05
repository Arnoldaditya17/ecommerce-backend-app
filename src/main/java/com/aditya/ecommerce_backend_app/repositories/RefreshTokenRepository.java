package com.aditya.ecommerce_backend_app.repositories;
import com.aditya.ecommerce_backend_app.models.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
   Optional<RefreshToken> findByToken(String token);

}
