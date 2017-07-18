import {Language} from "./language.enum";
import {Question} from "./question.interface";
import {TestSet} from "./testset.interface";

export interface Task {
  maxPoints: number;
}

export interface CodeTask extends Task {
  testSets: TestSet[];
  languages: Language[];
  timeout: number;
}

export interface QuizTask extends Task {
  name: string;
  questions: Question[];
}
