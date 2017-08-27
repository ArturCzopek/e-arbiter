import {Task} from "../interface/task.interface";

export interface TaskParser {
  parseStateFromStrData(task: Task): void;
  buildStrDataFromState(task: Task): void;
}
