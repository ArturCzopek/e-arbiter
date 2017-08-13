package pl.cyganki.tournament.service;

import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Service
public class HashingService {

    private static String PASSWORD_ENCODE_TYPE = "MD5";

    public static String getSecurePassword(String passwordToHash, byte[] salt) {
        String generatedPassword = null;

        try {
            MessageDigest md = MessageDigest.getInstance(PASSWORD_ENCODE_TYPE);
            md.update(salt);
            byte[] bytes = md.digest(passwordToHash.getBytes());
            StringBuilder sb = new StringBuilder();

            for(int i=0; i< bytes.length ;i++) {
                int widePosition = 0xff;
                int moveIndex = 0x100;
                sb.append(Integer.toString((bytes[i] & widePosition) + moveIndex, 16).substring(1));
            }
            generatedPassword = sb.toString();
        }
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return generatedPassword;
    }

}
