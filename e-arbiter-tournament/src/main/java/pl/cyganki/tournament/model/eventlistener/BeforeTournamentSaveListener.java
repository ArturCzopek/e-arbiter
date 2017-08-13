package pl.cyganki.tournament.model.eventlistener;

import com.mongodb.DBObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeSaveEvent;
import org.springframework.stereotype.Component;
import pl.cyganki.tournament.model.Tournament;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Component
public class BeforeTournamentSaveListener extends AbstractMongoEventListener<Tournament> {

    private static String PASSWORD_ENCODE_TYPE = "MD5";

    @Value("${e-arbiter.salt}")
    private int[] salts;

    private byte[] salt;

    @Override
    public void onBeforeSave(BeforeSaveEvent<Tournament> event) {
        Tournament source = event.getSource();

        //check if tournament is private - if not we escape from hashing
        if (source.isPublicFlag()) {
            return;
        }

        if (source.getPassword() == null || StringUtils.isBlank(source.getPassword())) {
            throw new RuntimeException("Password field in private tournaments cannot be blank or empty");
        }

        DBObject dbObject = event.getDBObject();

        String passwordToHash = source.getPassword();

        if (salts == null) {
            // if we do not provide salts in configuration file we just choose a random two numbers
            salts = new int []{1, 2};
        }
        else
            salt = integersToByte(salts);

        String securePassword = getSecurePassword(passwordToHash, salt);

        source.setPassword(securePassword);
        dbObject.put("password", securePassword);
    }

    private static String getSecurePassword(String passwordToHash, byte[] salt) {
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

    private static byte [] integersToByte(int [] salts) {
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream(salts.length * 4);
            DataOutputStream dos = new DataOutputStream(bos);
            for (int i = 0; i < salts.length; i++) {
                dos.writeInt(salts[i]);
            }

            return bos.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}

