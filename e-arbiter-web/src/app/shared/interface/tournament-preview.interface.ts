import {TournamentStatus} from "./tournament-status.enum";
import {LocalDateTime} from "./local-date-time.interface";

export interface TournamentPreview {
  id: string,
  ownerName: string,
  name: string,
  description: string,
  publicFlag: boolean,
  status: TournamentStatus,
  users: number,
  endDate: LocalDateTime
}
