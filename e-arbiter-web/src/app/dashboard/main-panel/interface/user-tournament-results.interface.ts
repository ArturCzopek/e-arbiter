import {SingleTaskResult} from './single-task-result.interface';

export interface UserTournamentResults {
  position: number,
  summaryPoints: number,
  taskResults: SingleTaskResult[],
  userName: string
}
