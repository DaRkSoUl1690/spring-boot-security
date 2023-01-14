package com.example.springbootsecuritytutorial.event.listener;

import com.example.springbootsecuritytutorial.entity.User;
import com.example.springbootsecuritytutorial.event.RegistrationCompleteEvent;
import com.example.springbootsecuritytutorial.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@Component
public class RegistrationCompleteEventListener implements ApplicationListener<RegistrationCompleteEvent> {

    @Autowired
    private UserService userService;

    @Override
    public void onApplicationEvent(RegistrationCompleteEvent event) {
        //Create the Verification Token for the User with link
        User user = event.getUser();
        String token = UUID.randomUUID().toString();
        userService.saveVerificationToken(token, user);
        //Send Mail to User
        String url = event.getApplicationUrl()
                + "/verifyRegistration?token="
                + token;

        //send verification Email()
        log.info("Click the link to verify your account: " + url);

    }
}
