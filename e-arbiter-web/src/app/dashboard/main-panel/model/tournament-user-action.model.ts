export class TournamentUserActionRequest {

  constructor(private action: TournamentUserActionType,
              private tournamentId: String,
              public password?: String) {
  }
}

export enum TournamentUserActionType {
  JOIN, LEAVE
};
