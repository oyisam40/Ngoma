package com.example.ngoma;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import java.time.LocalDateTime;

@RestController
public class EmailVerificationController {

    private final EmailVerificationTokenRepository tokenRepository;
    private final UserRepository userRepository;

    public EmailVerificationController(EmailVerificationTokenRepository tokenRepository,
                                       UserRepository userRepository) {
        this.tokenRepository = tokenRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/api/verify")
    public RedirectView verify(@RequestParam String token) {
        var tokenOpt = tokenRepository.findByToken(token);

        if (tokenOpt.isEmpty()) {
            return new RedirectView("/verify.html?status=invalid");
        }

        EmailVerificationToken verificationToken = tokenOpt.get();

        if (verificationToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            tokenRepository.delete(verificationToken);
            return new RedirectView("/verify.html?status=expired");
        }

        var userOpt = userRepository.findByEmail(verificationToken.getEmail());
        if (userOpt.isEmpty()) {
            return new RedirectView("/verify.html?status=invalid");
        }

        var user = userOpt.get();
        user.setEnabled(true);
        userRepository.save(user);

        tokenRepository.delete(verificationToken);

        return new RedirectView("/verify.html?status=success");
    }
}
