import {TaskParser} from "./task-parser";
import {Task} from "../interface/task.interface";
import {Answer} from "../interface/answer.interface";

export class QuizTaskParser implements TaskParser {

  public parseStateFromStrData(task: Task): void {
    // each question is represented as a separate paragraph
    // and all such paragraphs are delimited by '---'
    const paragraphs = task.strData.split(/^---$/m);

    task.questions = paragraphs.map(p => {
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
  }

  public buildStrDataFromState(task: Task): void {
    const answersToString = (answers: Answer[]): string => {
      return answers.map(a => 'ODP.' + (a.correct ? ' >>> ' : ' ') + a.content).join('\n');
    };

    const paragraphs = task.questions.map(
      q => q.content + '\nODPOWIEDZI\n' + answersToString(q.answers));

    task.strData = paragraphs.join('\n---\n');
  }
}
