import {Injectable} from "@angular/core";
import {default as TaskModel, Task} from "./interface/task.interface";
import {CodeTaskTestSet} from "./interface/code-task-test-set.interface";

@Injectable()
export class TaskService {

  public constituteTask(task: Task): boolean {
    if (task.type === TaskModel.taskTypes[0].value) {
      return this.constituteCodeTask(task);
    }

    return false;
  }

  private constituteCodeTask(task: Task): boolean {
    if (task.strData) {
      task.codeTaskTestSets = this.parseCodeStrData(task.strData);
    } else {
      task.strData = this.buildCodeStrData(task.codeTaskTestSets);
    }

    return true;
  }

  private parseCodeStrData(strData: string): CodeTaskTestSet[] {
    const codeTaskTestSets: CodeTaskTestSet[] = [];

    const lines = strData.split(/\n/);
    // a case is a sequence of characters surrounded by quotes or whitespaces
    const cases = lines.map(line => line.match(/(?:[^\s"]+|"[^"]*")+/g));

    cases.forEach(c => codeTaskTestSets.push({
      expectedResult: c[c.length - 1],
      parameters: c.slice(0, c.length - 1)
    }));

    return codeTaskTestSets;
  }

  private buildCodeStrData(codeTaskTestSets: CodeTaskTestSet[]): string {
    const lines = codeTaskTestSets.map(
      testSet => testSet.parameters.join(' ') + ' ' + testSet.expectedResult);

    return lines.join('\n');
  }

}
