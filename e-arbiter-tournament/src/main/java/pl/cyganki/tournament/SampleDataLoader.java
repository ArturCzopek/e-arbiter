package pl.cyganki.tournament;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pl.cyganki.tournament.model.JoinedTournament;
import pl.cyganki.tournament.model.Question;
import pl.cyganki.tournament.model.Tournament;
import pl.cyganki.tournament.model.User;
import pl.cyganki.tournament.repository.JoinedTournamentRepository;
import pl.cyganki.tournament.repository.QuestionRepository;
import pl.cyganki.tournament.repository.TournamentRepository;
import pl.cyganki.tournament.repository.UserRepository;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Component
public class SampleDataLoader implements ApplicationRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TournamentRepository tournamentRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private JoinedTournamentRepository joinedTournamentRepository;

    @Autowired
    private org.springframework.data.mongodb.core.MongoTemplate mongoTemplate;


    @Override
    @Transactional
    public void run(ApplicationArguments args) throws Exception {
        mongoTemplate.getDb().dropDatabase();

        Map<String, Boolean> answerSet = new HashMap<>();
        answerSet.put("Odpowiedz numer 1", true);
        answerSet.put("Odpowiedz numer 2", false);

        Question question = new Question(1, 5, "Testowe pytanie es numero 1", answerSet);

        Question question2 = new Question(2, 5, "Testowe pytanie es numero 2", answerSet);

        List<Question> questionsList = new LinkedList<>();
        questionsList.add(question);
        questionsList.add(question2);

        User user = new User(1, "Konrad Onieszczuk",null);

        User user2 = new User(2, "Maciej Marczak",null);

        User user3 = new User(3, "Artur Czopek",null);

        Tournament tournament = new Tournament(1, user, "Testowy turniej es numero 1", LocalDate.parse("2017-05-26"), LocalDate.parse("2017-05-27"),
                true, null, false, null, 0, null, null, true, null, 20);

        List<User> sharedUsers = new LinkedList<>();
        sharedUsers.add(user);
        sharedUsers.add(user3);

        Tournament tournament2 = new Tournament(2, user2, "Testowy turniej es numero 2", LocalDate.parse("2017-05-26"), LocalDate.parse("2017-05-27"),
                false, sharedUsers, true, "ruby", 0, "http://github.com/yoyo", null, false, questionsList, 10);

        JoinedTournament joinedTournament = new JoinedTournament(1, tournament, null, null, null);
        JoinedTournament joinedTournament2 = new JoinedTournament(2, tournament2, null, null, null);
        JoinedTournament joinedTournament3 = new JoinedTournament(3, tournament, null, null, null);
        JoinedTournament joinedTournament4 = new JoinedTournament(4, tournament2, null, null, null);

        questionRepository.save(question);
        questionRepository.save(question2);

        userRepository.save(user);
        userRepository.save(user2);
        userRepository.save(user3);

        tournamentRepository.save(tournament);
        tournamentRepository.save(tournament2);

        joinedTournamentRepository.save(joinedTournament);
        joinedTournamentRepository.save(joinedTournament2);
        joinedTournamentRepository.save(joinedTournament3);
        joinedTournamentRepository.save(joinedTournament4);

        List<JoinedTournament> joinedTournamentList = new LinkedList<>();
        joinedTournamentList.add(joinedTournament);
        joinedTournamentList.add(joinedTournament2);
        user3.setJoinedTournaments(joinedTournamentList);
        userRepository.save(user3);

        joinedTournamentList.clear();
        joinedTournamentList.add(joinedTournament3);
        user2.setJoinedTournaments(joinedTournamentList);
        userRepository.save(user2);

        joinedTournamentList.clear();
        joinedTournamentList.add(joinedTournament4);
        user.setJoinedTournaments(joinedTournamentList);
        userRepository.save(user);
    }}

