import {Injectable} from "@angular/core";
import {Task} from "./interface/task.interface";
import {CodeTaskTestSet} from "./interface/code-task-test-set.interface";

@Injectable()
export class TaskService {

  public constituteTask(task: Task): boolean {
    if (task.type === 'CodeTask') {
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
    const cases = lines.map(line => line.match(/(?:[^\s"]+|"[^"]*")+/g));

    cases.forEach(c => codeTaskTestSets.push({
      expectedResult: c[c.length - 1],
      parameters: c.slice(0, c.length - 1)
    }));

    return codeTaskTestSets;
  }

  private buildCodeStrData(codeTaskTestSets: CodeTaskTestSet[]): string {
    const surroundWithQuotes = str => '"' + str + '"';

    // add quotes for args with spaces
    codeTaskTestSets.forEach(testSet => {
      testSet.parameters = testSet.parameters
        .map(p => p.includes(' ') ? surroundWithQuotes(p) : p);

      if (testSet.expectedResult.includes(' ')) {
        testSet.expectedResult = surroundWithQuotes(testSet.expectedResult);
      }
    });

    const lines = codeTaskTestSets.map(
      testSet => testSet.parameters.join(' ') + ' ' + testSet.expectedResult);

    return lines.join('\n');
  }

}
