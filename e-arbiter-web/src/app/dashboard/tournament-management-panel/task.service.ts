import {Injectable} from "@angular/core";
import {Task} from "./interface/task.interface";
import {CodeTaskTestSet} from "./interface/code-task-test-set.interface";

@Injectable()
export class TaskService {

  public parseTaskData(task: Task, taskData: string): boolean {
    switch (task.type) {
      case 'CodeTask': return this.parseCodeTaskData(task, taskData);
      case 'QuizTask': return this.parseQuizTaskData(task, taskData);
      default:
        throw new Error(task.type + ' is not a valid task type');
    }
  }

  private parseCodeTaskData(task: Task, taskData: string): boolean {
    const codeTaskTestSets: CodeTaskTestSet[] = [];

    const lines = taskData.split(/\n/);
    const cases = lines.map(line => line.match(/(?:[^\s"]+|"[^"]*")+/g));

    cases.forEach(c => codeTaskTestSets.push({
      expectedResult: c[c.length - 1],
      parameters: c.slice(0, c.length - 1)
    }));

    task.codeTaskTestSets = codeTaskTestSets;

    return true;
  }

  private parseQuizTaskData(task: Task, taskData: string): boolean {
    return true;
  }

}
