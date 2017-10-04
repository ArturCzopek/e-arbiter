import {LocalDateTime} from '../../../shared/interface/local-date-time.interface';
import {AccessDetails} from './access-details.interface';
import {TournamentStatus} from '../../../shared/interface/tournament-status.enum';
import {TaskPreview} from '../../../shared/interface/task-preview.interface';

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
