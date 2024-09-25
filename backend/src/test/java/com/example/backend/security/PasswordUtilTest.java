package com.example.backend.security;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordUtilTest {

    @Test
    void testHashPassword() {
        String motDePasse = "testmdp";

        String motDePasseHashe = PasswordUtil.hashPassword(motDePasse);

        assertNotNull(motDePasseHashe);
        assertNotEquals(motDePasse, motDePasseHashe);

        // VVérification des mots de passe
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        assertTrue(encoder.matches(motDePasse, motDePasseHashe));
    }
}