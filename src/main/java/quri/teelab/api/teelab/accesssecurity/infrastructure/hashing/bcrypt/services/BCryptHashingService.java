package quri.teelab.api.teelab.accesssecurity.infrastructure.hashing.bcrypt.services;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import quri.teelab.api.teelab.accesssecurity.application.internal.outboundservices.IHashingService;

/**
 * BCrypt hashing service implementation
 */
@Service
public class BCryptHashingService implements IHashingService {

    private final PasswordEncoder passwordEncoder;

    public BCryptHashingService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public String hashPassword(String password) {
        return passwordEncoder.encode(password);
    }

    @Override
    public boolean verifyPassword(String password, String passwordHash) {
        return passwordEncoder.matches(password, passwordHash);
    }
}
