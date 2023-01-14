package com.example.springbootsecuritytutorial.controller;

import com.example.springbootsecuritytutorial.entity.User;
import com.example.springbootsecuritytutorial.entity.VerificationToken;
import com.example.springbootsecuritytutorial.event.RegistrationCompleteEvent;
import com.example.springbootsecuritytutorial.model.UserModel;
import com.example.springbootsecuritytutorial.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@Slf4j
public class RegistrationController {

    @Autowired
    private UserService userService;

    @Autowired
    private ApplicationEventPublisher publisher;

    @PostMapping("/register")
    public String registerUser(@RequestBody UserModel userModel,
                               final HttpServletRequest request) {
        try {
            User user = userService.registerUser(userModel);
            publisher.publishEvent(new RegistrationCompleteEvent(
                    user,
                    applicationUrl(request)
            ));
        } catch (Exception e) {
            log.error(e.getMessage());
        }

        return "Success";
    }

    @GetMapping("/verifyRegistration")
    public String verifyRegistration(@RequestParam("token") String token) {
        String result = userService.validateVerificationToken(token);

        if (result.equalsIgnoreCase("valid")) {
            return "User Verifies Successfully";
        }
        return "Bad User";
    }

    private @NotNull String applicationUrl(@NotNull HttpServletRequest request) {
        return "http://" +
                request.getServerName() +
                ":" +
                request.getServerPort() +
                request.getContextPath();
    }

    @GetMapping("/resendVerifyToken")
    public String resendVerification(@RequestParam("token") String oldToken,
                                     HttpServletRequest request) {
        VerificationToken verificationToken =
                userService.generateNewVerificationToken(oldToken);

        User user = verificationToken.getUser();

        resendVerificationMail(user, applicationUrl(request), verificationToken);
        return "Verification Link Sent";
    }

    private void resendVerificationMail(User user, String applicationUrl, VerificationToken verificationToken) {
        String url =
                applicationUrl
                        + "/verifyRegistration?token="
                        + verificationToken.getToken();

        log.info("Click the link to verify your account : {}",
                url);

    }


}
