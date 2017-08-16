import {Language} from "./language.enum";
import {Question} from "./question.interface";
import {CodeTaskTestSet} from "./code-task-test-set.interface";

export interface Task {
  description: string;
  name: string;
}

export interface CodeTask extends Task {
  codeTaskTestSets: CodeTaskTestSet[];
  languages: Language[];
  timeoutInMs: number;
}

export interface QuizTask extends Task {
  questions: Question[];
}
