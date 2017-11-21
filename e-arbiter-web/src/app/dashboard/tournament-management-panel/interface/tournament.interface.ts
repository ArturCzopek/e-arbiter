import {Task} from './task.interface';
import {TournamentStatus} from '../../../shared/interface/tournament-status.enum';

export interface Tournament {
  id?: string;
  ownerId?: number;
  name: string;
  startDate?: Date;
  endDate: Date;
  description: string;
  publicFlag: boolean;
  joinedUsersIds?: number[];
  resultsVisibleForJoinedUsers?: boolean;
  password?: string;
  status?: TournamentStatus;
  tasks: Task[];
}
