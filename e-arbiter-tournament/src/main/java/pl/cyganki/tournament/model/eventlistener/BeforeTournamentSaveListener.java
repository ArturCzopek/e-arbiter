package pl.cyganki.tournament.model.eventlistener;

import com.mongodb.DBObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeSaveEvent;
import org.springframework.stereotype.Component;
import pl.cyganki.tournament.model.Tournament;
import pl.cyganki.tournament.service.HashingService;

@Component
public class BeforeTournamentSaveListener extends AbstractMongoEventListener<Tournament> {

    private HashingService hashingService;

    @Autowired
    public BeforeTournamentSaveListener(HashingService hashingService) {
        this.hashingService = hashingService;
    }

    @Override
    public void onBeforeSave(BeforeSaveEvent<Tournament> event) {
        Tournament source = event.getSource();

        //check if tournament is private - if not we escape from hashing
        if (source.isPublicFlag()) {
            return;
        }

        if (StringUtils.isBlank(source.getPassword())) {
            throw new RuntimeException("Password field in private tournaments cannot be blank or empty");
        }

        DBObject dbObject = event.getDBObject();
        String passwordToHash = source.getPassword();

        String securePassword = hashingService.getSecurePassword(passwordToHash);

        source.setPassword(securePassword);
        dbObject.put("password", securePassword);
    }
}