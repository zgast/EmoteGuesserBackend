package at.markus.EmoteGuesserBackend.security;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Base64;

public class Cryptography {
    public static String hash(String token) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            md.update(Keys.SALT);
            return Base64.getEncoder().withoutPadding().encodeToString(md.digest(token.getBytes(StandardCharsets.UTF_8)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return token;
    }
    public static String createToken(int length)  {
        final String allowedChars = "0123456789abcdefghijklmnopqrstuvwABCDEFGHIJKLMNOP";
        SecureRandom random = new SecureRandom();
        StringBuilder pass = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            pass.append(allowedChars.charAt(random.nextInt(allowedChars.length())));
        }

        return pass.toString();
    }
}
