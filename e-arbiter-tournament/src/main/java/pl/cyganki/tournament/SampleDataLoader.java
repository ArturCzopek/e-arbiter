package pl.cyganki.tournament;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.core.MongoTemplate;
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
import java.util.*;

@Profile("dev")
@Component
public class SampleDataLoader implements ApplicationRunner {

    private UserRepository userRepository;
    private TournamentRepository tournamentRepository;
    private QuestionRepository questionRepository;
    private JoinedTournamentRepository joinedTournamentRepository;
    private MongoTemplate mongoTemplate;

    @Autowired
    public SampleDataLoader(UserRepository userRepository, TournamentRepository tournamentRepository,
                            QuestionRepository questionRepository,  JoinedTournamentRepository joinedTournamentRepository,
                            MongoTemplate mongoTemplate) {
        this.userRepository = userRepository;
        this.tournamentRepository = tournamentRepository;
        this.questionRepository = questionRepository;
        this.joinedTournamentRepository = joinedTournamentRepository;
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    @Transactional
    public void run(ApplicationArguments args) throws Exception {
        mongoTemplate.getDb().dropDatabase();

        Map<String, Boolean> answerSet = new HashMap<>();
        answerSet.put("Odpowiedz numer 1", true);
        answerSet.put("Odpowiedz numer 2", false);

        List <Question> questionsList = Arrays.asList(Question.builder().questionId(1).value(5).content("Testowe pytanie es numero 1").answers(answerSet).build(),
                Question.builder().questionId(2).value(5).content("Testowe pytanie es numero 2").answers(answerSet).build()
        );

        List<User> usersList = Arrays.asList(User.builder().userId(1).name("Konrad Onieszczuk").build(),
                User.builder().userId(2).name("Maciej Marczak").build(),
                User.builder().userId(3).name("Artur Czopek").build()
        );

        List<User> sharedUsers = Arrays.asList(
                usersList.get(0),
                usersList.get(2)
        );


        List<Tournament> tournamentsList = Arrays.asList(
                Tournament.builder().tournamentId(1).owner(usersList.get(0)).name("Testowy turniej es numero 1").startDate(LocalDate.parse("2017-05-26"))
                        .endDate(LocalDate.parse("2017-05-27")).publicFlag(true).codeFlag(false).timeout(0).testFlag(true).maxPoints(20).build(),
                Tournament.builder().tournamentId(2).owner(usersList.get(1)).name("Testowy turniej es numero 2").startDate(LocalDate.parse("2017-05-26"))
                        .endDate(LocalDate.parse("2017-05-27")).publicFlag(true).sharedUsers(sharedUsers).codeFlag(true)
                        .language(Tournament.Language.JAVA).timeout(0).solution("http://github.com/yoyo").testFlag(true).questions(questionsList).maxPoints(10).build()
        );


        List<JoinedTournament> joinedTournamentsList = Arrays.asList(
                JoinedTournament.builder().joinedTournamentId(1).tournament(tournamentsList.get(0)).build(),
                JoinedTournament.builder().joinedTournamentId(2).tournament(tournamentsList.get(1)).build(),
                JoinedTournament.builder().joinedTournamentId(3).tournament(tournamentsList.get(0)).build(),
                JoinedTournament.builder().joinedTournamentId(4).tournament(tournamentsList.get(1)).build()
        );

        questionRepository.save(questionsList);

        userRepository.save(usersList);

        tournamentRepository.save(tournamentsList);

        joinedTournamentRepository.save(joinedTournamentsList);


        List<JoinedTournament> joinedTournamentListToAdd = new ArrayList<>();

        joinedTournamentListToAdd.add(joinedTournamentsList.get(0));
        joinedTournamentListToAdd.add(joinedTournamentsList.get(1));
        usersList.get(2).setJoinedTournaments(joinedTournamentListToAdd);
        userRepository.save(usersList.get(2));

        joinedTournamentListToAdd.clear();
        joinedTournamentListToAdd.add(joinedTournamentsList.get(2));
        usersList.get(1).setJoinedTournaments(joinedTournamentListToAdd);
        userRepository.save(usersList.get(1));

        joinedTournamentListToAdd.clear();
        joinedTournamentListToAdd.add(joinedTournamentsList.get(3));
        usersList.get(0).setJoinedTournaments(joinedTournamentListToAdd);
        userRepository.save(usersList.get(0));
    }}

