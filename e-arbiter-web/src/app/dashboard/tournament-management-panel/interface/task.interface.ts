import {Language} from "./language.enum";
import {Question} from "./question.interface";
import {CodeTaskTestSet} from "./code-task-test-set.interface";

export const TaskTypes = [
  {
    value: 'CodeTask',
    display: 'Zadanie programistyczne'
  },
  {
    value: 'QuizTask',
    display: 'Quiz'
  }
];

export interface Task {
  type: string;
  name: string;
  description: string;
  codeTaskTestSets?: CodeTaskTestSet[];
  languages?: Language[];
  timeoutInMs?: number;
  questions?: Question[];
}
