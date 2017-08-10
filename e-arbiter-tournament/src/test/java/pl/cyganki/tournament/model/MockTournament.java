package pl.cyganki.tournament.model;

import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
public class MockTournament extends Tournament {

    public MockTournament(TournamentStatus status, LocalDateTime endDate) {
        super();
        this.status = status;
        this.endDate = endDate;
    }

    public static MockTournament getDraft() {
        MockTournament tournament = new MockTournament();
        tournament.status = TournamentStatus.DRAFT;
        return tournament;
    }

    public static MockTournament getActive() {
        MockTournament tournament = new MockTournament();
        tournament.status = TournamentStatus.ACTIVE;
        return tournament;
    }

    public static MockTournament getFinished() {
        MockTournament tournament = new MockTournament();
        tournament.status = TournamentStatus.FINISHED;
        return tournament;
    }
}
