package com.aditya.user.repositories;

import com.aditya.user.models.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;  // Import this to use @Param
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<Token, Integer> {
    @Query("""
            select t from Token t inner join User u 
            on t.user.id = u.id
            where t.user.id = :userId and t.loggedOut=false
            """)
    List<Token> findAllTokenByUser(@Param("userId") Integer userId);  // Add @Param here

    Optional<Token> findByToken(String token);
}
