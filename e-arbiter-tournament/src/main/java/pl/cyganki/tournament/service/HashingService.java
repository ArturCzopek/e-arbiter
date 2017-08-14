package pl.cyganki.tournament.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Service
public class HashingService {

    private final static String PASSWORD_ENCODE_TYPE = "MD5";
    private final static int NUMERIC_SYSTEM = 16;
    private final static int BITS_PER_INT = 4;
    private final static int WIDE_POSITION = 0xff;
    private final static int MOVE_INDEX = 0x100;

    @Value("${e-arbiter.salt:1,2}")
    private int[] saltAsInts;

    public String getSecurePassword(String passwordToHash) {
        String generatedPassword = null;

        byte[] saltAsBytes = getSaltAsBytes();

        try {
            MessageDigest md = MessageDigest.getInstance(PASSWORD_ENCODE_TYPE);
            md.update(saltAsBytes);
            byte[] bytes = md.digest(passwordToHash.getBytes());
            StringBuilder sb = new StringBuilder();

            for (int i = 0; i < bytes.length; i++) {

                sb.append(Integer.toString((bytes[i] & WIDE_POSITION) + MOVE_INDEX, NUMERIC_SYSTEM).substring(1));
            }
            generatedPassword = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return generatedPassword;
    }

    public boolean checkPassword(String passwordToCheck, String hashedPassword) {
        String hashedPasswordToCheck = getSecurePassword(passwordToCheck);

        return hashedPassword.equals(hashedPasswordToCheck);
    }

    private byte[] getSaltAsBytes() {
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream(this.saltAsInts.length * BITS_PER_INT);
            DataOutputStream dos = new DataOutputStream(bos);

            for (int saltAsInt : this.saltAsInts) {
                dos.writeInt(saltAsInt);
            }

            return bos.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
