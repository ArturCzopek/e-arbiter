import {Language} from "./language.enum";
import {Question} from "./question.interface";
import {CodeTaskTestSet} from "./code-task-test-set.interface";

export interface Task {
  description: string;
}

export interface CodeTask extends Task {
  codeTaskTestSets: CodeTaskTestSet[];
  languages: Language[];
  timeoutInMs: number;
}

export interface QuizTask extends Task {
  name: string;
  questions: Question[];
}
