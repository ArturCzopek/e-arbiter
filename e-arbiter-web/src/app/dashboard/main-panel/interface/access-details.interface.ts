export interface AccessDetails {
  publicFlag: boolean,
  owner: boolean,
  participateInTournament: boolean,
  resultsVisible: boolean
}

export class TmpAccessDetails implements AccessDetails {
  constructor(public publicFlag: boolean,
              public owner: boolean,
              public participateInTournament: boolean,
              public resultsVisible: boolean) {

  }
}
