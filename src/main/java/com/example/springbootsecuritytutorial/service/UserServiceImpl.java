package com.example.springbootsecuritytutorial.service;

import com.example.springbootsecuritytutorial.entity.User;
import com.example.springbootsecuritytutorial.entity.VerificationToken;
import com.example.springbootsecuritytutorial.model.UserModel;
import com.example.springbootsecuritytutorial.repository.UserRepository;
import com.example.springbootsecuritytutorial.repository.VerificationTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private VerificationTokenRepository verificationTokenRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public User registerUser(UserModel userModel) {
        User user = new User();
        user.setEmail(userModel.getEmail());
        user.setFirstName(userModel.getFirstName());
        user.setLastName(userModel.getLastName());
        user.setRole("USER");
        user.setPassword(passwordEncoder.encode(userModel.getPassword()));
        userRepository.save(user);
        return user;
    }

    @Override
    public void saveVerificationToken(String token, User user) {
        VerificationToken verificationToken
                = new VerificationToken(user,token);

        verificationTokenRepository.save(verificationToken);

    }
}
