package pl.cyganki.tournament.model.eventlistener;

import com.mongodb.DBObject;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeSaveEvent;
import org.springframework.stereotype.Component;
import pl.cyganki.tournament.model.Tournament;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Component
public class BeforeTournamentSaveListener extends AbstractMongoEventListener<Tournament> {

    @Override
    public void onBeforeSave(BeforeSaveEvent<Tournament> event) {
        Tournament source = event.getSource();

        if (source.getPassword() == null) {
            return;
        }

        DBObject dbObject = event.getDBObject();

        String passwordToHash = source.getPassword();
        // for now simple salt
        byte[] salt = new byte[]{1, 2, 3, 4};

        String securePassword = getSecurePassword(passwordToHash, salt);

        source.setPassword(securePassword);
        dbObject.put("password", securePassword);
    }

    private static String getSecurePassword(String passwordToHash, byte[] salt) {
        String generatedPassword = null;

        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(salt);
            byte[] bytes = md.digest(passwordToHash.getBytes());
            StringBuilder sb = new StringBuilder();
            for(int i=0; i< bytes.length ;i++)
            {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }

            generatedPassword = sb.toString();
        }
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return generatedPassword;
    }

}

