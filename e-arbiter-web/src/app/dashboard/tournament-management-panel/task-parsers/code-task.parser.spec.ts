import {CodeTaskParser} from './code-task.parser';
import {Task} from '../interface/task.interface';
import {CodeTaskTestSet} from '../interface/code-task-test-set.interface';

describe('CodeTaskParser', () => {
  let codeTaskParser: CodeTaskParser;

  beforeEach(() => {
    codeTaskParser = new CodeTaskParser();
  });

  it('#parseStringDataFromTaskToTask should parse valid string to task', () => {
    const taskWithValidString = new MockTask();
    taskWithValidString.strData = 'ala ma kota "ala ma kota"\n1 2 3 4 24'

    const parsedTask = codeTaskParser.parseStringDataFromTaskToTask(taskWithValidString);
    expect(parsedTask.strData).toBe(taskWithValidString.strData);
    expect(parsedTask.codeTaskTestSets.length).toBe(2);
    expect(parsedTask.codeTaskTestSets[0].parameters.length).toBe(3);
    expect(parsedTask.codeTaskTestSets[0].expectedResult).toBe('\"ala ma kota\"');
    expect(parsedTask.codeTaskTestSets[1].parameters.length).toBe(4);
    expect(parsedTask.codeTaskTestSets[1].expectedResult).toBe('24');

  });

  it('#parseStringDataFromTaskToTask should not parse string with too less arguments', () => {
    const taskWithInvalidString = new MockTask();
    taskWithInvalidString.strData = 'ala ma kota "ala ma kota"\n1'

    expect(() => codeTaskParser.parseStringDataFromTaskToTask(taskWithInvalidString)).toThrow(Error('Too less arguments to execute'));
  });

  it('#parseTaskToString should parse valid task to string', () => {
    const validTaskToParse = new MockTask();

    validTaskToParse.codeTaskTestSets = [
      new MockCodeTaskTestSet(
        '\"ala ma kota\"',
        ['ala', 'ma', 'kota']
      ),
      new MockCodeTaskTestSet(
        '24',
        ['1', '2', '3', '4']
      )
    ];

    const parsedTask = codeTaskParser.parseTaskToString(validTaskToParse);

    const expectedParsedTask = 'ala ma kota "ala ma kota"\n1 2 3 4 24'

    expect(parsedTask).toBe(expectedParsedTask);
  });
});

class MockTask implements Task {
  type: string;
  name: string;
  description: string;
  strData: string;
  codeTaskTestSets: CodeTaskTestSet[];

  constructor() {
  }
}

class MockCodeTaskTestSet implements CodeTaskTestSet {
  constructor(public expectedResult: string,
              public parameters: string[]) {

  }
}
