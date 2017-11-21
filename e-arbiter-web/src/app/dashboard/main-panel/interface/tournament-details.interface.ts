import {LocalDateTime} from '../../../shared/interface/local-date-time.interface';
import {AccessDetails} from './access-details.interface';
import {TaskPreview} from '../../../shared/interface/task-preview.interface';

export interface TournamentDetails {
  id: string,
  ownerName: string,
  name: string,
  accessDetails: AccessDetails,
  status: string,

  // nullable fields depending on user access to tournament
  // if user, for example, doesn't have access to private tournament,
  // some data shouldn't be sent
  description?: string,
  users?: number,
  startDate?: LocalDateTime,
  endDate?: LocalDateTime,
  taskPreviews?: TaskPreview[],
  maxPoints?: number,
  earnedPoints?: number,
  position?: number,
};
