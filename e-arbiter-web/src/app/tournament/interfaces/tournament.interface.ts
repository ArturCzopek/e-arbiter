import {Task} from "./task.interface";

export interface Tournament {
  id?: string;
  ownerId: number;
  name: string;
  description: string;
  startDate?: Date;
  endDate: Date;
  publicFlag: boolean;
  joinedUsersId?: number[];
  resultsVisibleForJoinedUsers?: boolean;
  password?: string;
  tasks: Task[];
}
