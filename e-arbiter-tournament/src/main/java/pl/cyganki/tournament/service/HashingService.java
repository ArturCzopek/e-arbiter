package pl.cyganki.tournament.service;

import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Service
public class HashingService {

    private final static String PASSWORD_ENCODE_TYPE = "MD5";
    private final static int NUMERIC_SYSTEM = 16;

    public String getSecurePassword(String passwordToHash, byte[] salt) {
        String generatedPassword = null;

        try {
            MessageDigest md = MessageDigest.getInstance(PASSWORD_ENCODE_TYPE);
            md.update(salt);
            byte[] bytes = md.digest(passwordToHash.getBytes());
            StringBuilder sb = new StringBuilder();

            for (int i = 0; i < bytes.length; i++) {
                int widePosition = 0xff;
                int moveIndex = 0x100;
                sb.append(Integer.toString((bytes[i] & widePosition) + moveIndex, NUMERIC_SYSTEM).substring(1));
            }
            generatedPassword = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return generatedPassword;
    }

    public boolean checkPassword(String passwordToCheck, String hashedPassword, byte[] salt) {
        String hashedPasswordToCheck = getSecurePassword(passwordToCheck, salt);

        return hashedPasswordToCheck.equals(hashedPassword);
    }
}
