import {TournamentStatus} from "./tournament-status.enum";

export interface TournamentPreview {
  id: string,
  ownerName: string,
  name: string,
  description: string,
  publicFlag: boolean,
  status: TournamentStatus,
  users: number,
  endDate: Date
}
