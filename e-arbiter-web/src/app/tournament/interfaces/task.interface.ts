import {Language} from "./language.enum";
import {Question} from "./question.interface";
import {CodeTaskTestSet} from "./testset.interface";

export interface Task {}

export interface CodeTask extends Task {
  codeTaskTestSets: CodeTaskTestSet[];
  languages: Language[];
  timeoutInMs: number;
}

export interface QuizTask extends Task {
  name: string;
  questions: Question[];
}
