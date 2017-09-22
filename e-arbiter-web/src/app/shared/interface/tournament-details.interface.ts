import {LocalDateTime} from './local-date-time.interface';
import {AccessDetails} from './access-details.interface';
import {TournamentStatus} from './tournament-status.enum';
import {TaskPreview} from './task-preview.interface';

export interface TournamentDetails {
  id: string,
  ownerName: string,
  name: string,
  accessDetails: AccessDetails,
  status: TournamentStatus,


  // nullable fields depending on user access to tournament
  // if user, for example, doesn't have access to private tournament,
  // some data shouldn't be sent
  description?: string,
  users?: number,
  startDate?: LocalDateTime,
  endDate?: LocalDateTime,
  taskPreviews?: TaskPreview[],
  maxPoints?: number,
  userPoints?: number
};

// TODO: Remove it in #84
export class TmpTournamentDetails implements TournamentDetails {
  constructor(public id: string,
              public ownerName: string,
              public name: string,
              public accessDetails: AccessDetails,
              public status: TournamentStatus,
              public description?: string,
              public users?: number,
              public startDate?: LocalDateTime,
              public endDate?: LocalDateTime,
              public taskPreviews?: TaskPreview[],
              public maxPoints?: number,
              public userPoints?: number) {

  }
}
