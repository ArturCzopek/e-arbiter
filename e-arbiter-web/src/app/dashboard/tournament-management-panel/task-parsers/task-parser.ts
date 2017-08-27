import {Task} from "../interface/task.interface";

export abstract class TaskParser {
  public abstract constituteTask(task: Task): boolean;
}
