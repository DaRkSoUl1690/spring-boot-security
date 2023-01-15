package com.example.springbootsecuritytutorial.repository;

import com.example.springbootsecuritytutorial.entity.PasswordResetToken;
import com.example.springbootsecuritytutorial.model.PasswordModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken,Long> {
    PasswordResetToken findByToken(String token);
}
