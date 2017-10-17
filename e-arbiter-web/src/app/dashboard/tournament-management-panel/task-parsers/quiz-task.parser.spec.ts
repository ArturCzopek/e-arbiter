import {QuizTaskParser} from './quiz-task.parser';
import {Task} from '../interface/task.interface';
import {Question} from '../interface/question.interface';
import {Answer} from '../interface/answer.interface';

describe('QuizTaskParser', () => {
  let quizTaskParser: QuizTaskParser;

  beforeEach(() => {
    quizTaskParser = new QuizTaskParser();
  });

  it('#parseStringDataFromTaskToTask should parse valid string to task', () => {
    const taskWithValidString = new MockTask();
    taskWithValidString.strData = 'PROBY 3\n---\nWiecej niz jedno zwierze to?'
      + '\nODPOWIEDZI\nODP. owca\nODP. >>>lama\n---'
      + '\nJestes lama?\nODPOWIEDZI\nODP. >>>tak\nODP. moze\nODP. nie';

    const parsedTask = quizTaskParser.parseStringDataFromTaskToTask(taskWithValidString);
    expect(parsedTask.strData).toBe(taskWithValidString.strData);
    expect(parsedTask.maxAttempts).toBe(3);
    expect(parsedTask.questions.length).toBe(2);

    expect(parsedTask.questions[0].content).toBe('Wiecej niz jedno zwierze to?');
    expect(parsedTask.questions[0].answers.length).toBe(2);
    expect(parsedTask.questions[0].answers[0].content).toBe('owca');
    expect(parsedTask.questions[0].answers[0].correct).toBe(false);
    expect(parsedTask.questions[0].answers[1].content).toBe('lama');
    expect(parsedTask.questions[0].answers[1].correct).toBe(true);

    expect(parsedTask.questions[1].content).toBe('Jestes lama?');
    expect(parsedTask.questions[1].answers.length).toBe(3);
    expect(parsedTask.questions[1].answers[0].content).toBe('tak');
    expect(parsedTask.questions[1].answers[0].correct).toBe(true);
    expect(parsedTask.questions[1].answers[1].content).toBe('moze');
    expect(parsedTask.questions[1].answers[1].correct).toBe(false);
    expect(parsedTask.questions[1].answers[2].content).toBe('nie');
    expect(parsedTask.questions[1].answers[2].correct).toBe(false);
  });

  it('#parseStringDataFromTaskToTask should not parse string with max attempts with too much args', () => {
    const taskWithInvalidString = new MockTask();
    taskWithInvalidString.strData = 'PROBY 3 44\n---\nWiecej niz jedno zwierze to?'
      + '\nODPOWIEDZI\nODP. owca\nODP. >>>lama\n---'
      + '\nJestes lama?\nODPOWIEDZI\nODP. >>>tak\nODP. moze\nODP. nie';

    expect(() => quizTaskParser.parseStringDataFromTaskToTask(taskWithInvalidString)).toThrow(new Error('Invalid max attempts definition'));
  });

  it('#parseStringDataFromTaskToTask should not parse string without max attempts', () => {
    const taskWithInvalidString = new MockTask();
    taskWithInvalidString.strData = 'Wiecej niz jedno zwierze to?'
      + '\nODPOWIEDZI\nODP. owca\nODP. >>>lama\n---'
      + '\nJestes lama?\nODPOWIEDZI\nODP. >>>tak\nODP. moze\nODP. nie';

    expect(() => quizTaskParser.parseStringDataFromTaskToTask(taskWithInvalidString)).toThrow(new Error('Invalid max attempts definition'));
  });

  it('#parseStringDataFromTaskToTask should not parse string without max attempts number', () => {
    const taskWithInvalidString = new MockTask();
    taskWithInvalidString.strData = 'PROBY \n---\nWiecej niz jedno zwierze to?'
      + '\nODPOWIEDZI\nODP. owca\nODP. >>>lama\n---'
      + '\nJestes lama?\nODPOWIEDZI\nODP. >>>tak\nODP. moze\nODP. nie';

    expect(() => quizTaskParser.parseStringDataFromTaskToTask(taskWithInvalidString)).toThrow(new Error('Invalid max attempts definition'));
  });

  it('#parseStringDataFromTaskToTask should not parse string with max attempts as a string', () => {
    const taskWithInvalidString = new MockTask();
    taskWithInvalidString.strData = 'PROBY trzy\n---\nWiecej niz jedno zwierze to?'
      + '\nODPOWIEDZI\nODP. owca\nODP. >>>lama\n---'
      + '\nJestes lama?\nODPOWIEDZI\nODP. >>>tak\nODP. moze\nODP. nie';

    expect(() => quizTaskParser.parseStringDataFromTaskToTask(taskWithInvalidString)).toThrow(new Error('Invalid max attempts definition'));
  });

  it('#parseStringDataFromTaskToTask should not parse string with max attempts less than 1', () => {
    const taskWithInvalidString = new MockTask();
    taskWithInvalidString.strData = 'PROBY -13\n---\nWiecej niz jedno zwierze to?'
      + '\nODPOWIEDZI\nODP. owca\nODP. >>>lama\n---'
      + '\nJestes lama?\nODPOWIEDZI\nODP. >>>tak\nODP. moze\nODP. nie';

    expect(() => quizTaskParser.parseStringDataFromTaskToTask(taskWithInvalidString)).toThrow(new Error('Invalid max attempts definition'));
  });

  it('#parseStringDataFromTaskToTask should not parse string with question with one answer', () => {
    const taskWithInvalidString = new MockTask();
    taskWithInvalidString.strData = 'PROBY 4\n---\nWiecej niz jedno zwierze to?'
      + '\nODPOWIEDZI\nODP. owca\nODP. >>>lama\n---'
      + '\nJestes lama?\nODPOWIEDZI\nODP. >>>tak';

    expect(() => quizTaskParser.parseStringDataFromTaskToTask(taskWithInvalidString)).toThrow(new Error('Each question should have at least two answers.'));
  });

  it('#parseTaskToString should parse valid task to string', () => {
    const validTaskToParse = new MockTask();
    validTaskToParse.maxAttempts = 3;
    validTaskToParse.questions = [
      new MockQuestion('Wiecej niz jedno zwierze to?',
        [
          new MockAnswer('owca', false),
          new MockAnswer('lama', true)
        ]),
      new MockQuestion('Jestes lama?',
        [
          new MockAnswer('tak', true),
          new MockAnswer('moze', false),
          new MockAnswer('nie', false)
        ]),
    ];

    const parsedTask = quizTaskParser.parseTaskToString(validTaskToParse);

    const expectedParsedTask = 'PROBY 3\n---\nWiecej niz jedno zwierze to?'
      + '\nODPOWIEDZI\nODP. owca\nODP. >>>lama\n---'
      + '\nJestes lama?\nODPOWIEDZI\nODP. >>>tak\nODP. moze\nODP. nie';

    expect(parsedTask).toBe(expectedParsedTask);
  });

});

class MockTask implements Task {
  type: string;
  name: string;
  description: string;
  strData: string;
  questions?: Question[];
  maxAttempts?: number;

  constructor() {
  }
}

class MockQuestion implements Question {
  constructor(public content: string, public answers: Answer[]) {
  }
}

class MockAnswer implements Answer {

  constructor(public content: string, public correct: boolean) {
  }
}
