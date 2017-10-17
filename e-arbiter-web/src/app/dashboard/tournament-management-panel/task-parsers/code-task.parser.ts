import {TaskParser} from './task.parser';
import {Task} from '../interface/task.interface';
import {CodeTaskTestSet} from '../interface/code-task-test-set.interface';

export class CodeTaskParser implements TaskParser {

  public parseStringDataFromTaskToTask(task: Task): Task {
    const codeTaskTestSets: CodeTaskTestSet[] = [];

    const lines = task.strData.split(/\n/);
    // a case is a sequence of characters surrounded by quotes or whitespaces
    const cases = lines.map(line => line.match(/(?:[^\s"]+|"[^"]*")+/g));

    if (cases.find(c => c.length < 2)) {
      throw new Error('Too less arguments to execute');
    }

    cases.forEach(c => codeTaskTestSets.push({
      expectedResult: c[c.length - 1],
      parameters: c.slice(0, c.length - 1)
    }));

    task.codeTaskTestSets = codeTaskTestSets;

    return task;
  }

  public parseTaskToString(task: Task): string {
    const lines = task.codeTaskTestSets.map(
      testSet => testSet.parameters.join(' ') + ' ' + testSet.expectedResult);

    return lines.join('\n');
  }
}
