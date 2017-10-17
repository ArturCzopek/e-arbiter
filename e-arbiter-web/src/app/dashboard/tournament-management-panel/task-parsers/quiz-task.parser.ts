import {TaskParser} from './task.parser';
import {Task} from '../interface/task.interface';
import {Answer} from '../interface/answer.interface';
import * as _ from 'lodash';

export class QuizTaskParser implements TaskParser {

  public parseStringDataFromTaskToTask(task: Task): Task {
    // each question is represented as a separate paragraph
    // and all such paragraphs are delimited by '---'
    const paragraphs = task.strData.split(/^---$/m);
    const splitMaxAttemptsParagraph = paragraphs[0].split(' ');  // for construction 'PROBY value' there should be only two splitted values

    if (this.isMaxAttemptsInvalid(splitMaxAttemptsParagraph)) {
      throw new Error('Invalid max attempts definition');
    }

    task.maxAttempts = +splitMaxAttemptsParagraph[1];

    task.questions = paragraphs
      .filter((value, index) => index > 0)  // first value with index 0 is a value of max attempts
      .map(p => {
      // a single line 'ODPOWIEDZI' separates content from answers
      const contentAndAnswers = p.split(/^ODPOWIEDZI$/m);
      const question = {
        content: contentAndAnswers[0].trim(),
        answers: contentAndAnswers[1]
          .split(/^ODP\./m)
          .filter(a => a && a !== '\n' && a.trim())
          .map(a => {
            const isCorrect = a.trim().startsWith('>>>');
            return {
              content: isCorrect ? a.replace('>>>', '').trim() : a.trim(),
              correct: isCorrect
            }
          })
      };

      if (question.answers.length < 2) {
        throw new Error('Each question should have at least two answers.');
      }

      return question;
    });

    return task;
  }

  public parseTaskToString(task: Task): string {

    const maxAttemptsToString = (maxAttempts: number): string => `PROBY ${maxAttempts}`;

    const answersToString = (answers: Answer[]): string => {
      return answers.map(a => 'ODP.' + (a.correct ? ' >>>' : ' ') + a.content).join('\n');
    };

    const paragraphs = [
      maxAttemptsToString(task.maxAttempts),
      ...task.questions.map(q => q.content + '\nODPOWIEDZI\n' + answersToString(q.answers))
    ];

    return paragraphs.join('\n---\n');
  }

  private isMaxAttemptsInvalid(splitMaxAttemptsParagraph: string[]) {
    return splitMaxAttemptsParagraph.length !== 2 || splitMaxAttemptsParagraph[0] !== 'PROBY'
      || !_.isNumber(+splitMaxAttemptsParagraph[1]) || isNaN(+splitMaxAttemptsParagraph[1])
      || +splitMaxAttemptsParagraph[1] < 1;
  }
}
