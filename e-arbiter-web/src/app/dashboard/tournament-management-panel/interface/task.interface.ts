import {Question} from "./question.interface";
import {CodeTaskTestSet} from "./code-task-test-set.interface";
import {CodeTaskParser} from "../task-parsers/code-task-parser";
import {QuizTaskParser} from "../task-parsers/quiz-task-parser";

const TaskModel = {
  taskTypes: [
    {
      value: 'CodeTask',
      display: 'Zadanie programistyczne',
      parser: new CodeTaskParser()
    },
    {
      value: 'QuizTask',
      display: 'Quiz',
      parser: new QuizTaskParser()
    }
  ],
  languages: ['JAVA', 'PYTHON', 'C11', 'CPP'],
  createEmptyTask: () => {
    return {
      type: TaskModel.taskTypes[0].value,
      name: '',
      description: '',
      codeTaskTestSets: [],
      questions: [],
      timeoutInMs: 1000,
      languages: ['C11'],
      strData: ''
    };
  }
};

export interface Task {
  type: string;
  name: string;
  description: string;
  codeTaskTestSets?: CodeTaskTestSet[];
  languages?: string[];
  timeoutInMs?: number;
  questions?: Question[];
  strData?: string;
}

export default TaskModel;
