import {Task} from "./task.interface";

export interface Tournament {
  id?: string;
  ownerId: number;
  name: string;
  startDate?: Date;
  endDate: Date;
  description: string;
  publicFlag: boolean;
  joinedUsersId?: number[];
  resultsVisibleForJoinedUsers?: boolean;
  password?: string;
  tasks: Task[];
}
