import {Task} from '../interface/task.interface';

export interface TaskParser {
  parseStringDataFromTaskToTask(task: Task): Task;
  parseTaskToString(task: Task): string;
}
