package pl.cyganki.tournament.model.eventlistener;

import com.mongodb.DBObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeSaveEvent;
import org.springframework.stereotype.Component;
import pl.cyganki.tournament.model.Tournament;
import pl.cyganki.tournament.service.HashingService;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

@Component
public class BeforeTournamentSaveListener extends AbstractMongoEventListener<Tournament> {

    private HashingService hashingService;

    @Autowired
    public BeforeTournamentSaveListener(HashingService hashingService) {
        this.hashingService = hashingService;
    }

    @Value("${e-arbiter.salt:1,2}")
    private int[] saltAsInts;

    private byte[] saltAsBytes;

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

        saltAsBytes = integersToByte(saltAsInts);

        String securePassword = hashingService.getSecurePassword(passwordToHash, saltAsBytes);

        source.setPassword(securePassword);
        dbObject.put("password", securePassword);
    }

    private byte[] integersToByte(int[] salts) {
        try {
            final int bitsPerSaltInt = 4;
            ByteArrayOutputStream bos = new ByteArrayOutputStream(salts.length * bitsPerSaltInt);
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