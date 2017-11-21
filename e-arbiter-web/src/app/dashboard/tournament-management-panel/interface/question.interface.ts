import {Answer} from './answer.interface';

export interface Question {
  content: string;
  answers: Answer[];
}
