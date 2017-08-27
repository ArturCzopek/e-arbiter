import {TaskParser} from "./task-parser";
import {Task} from "../interface/task.interface";
import {Question} from "../interface/question.interface";
import {Answer} from "../interface/answer.interface";

export class QuizTaskParser implements TaskParser {
  constituteTask(task: Task): boolean {
    if (task.strData) {
      task.questions = this.parseQuizStrData(task.strData);
    } else {
      task.strData = this.buildQuizStrData(task.questions);
    }

    return true;
  }

  private parseQuizStrData(strData: string): Question[] {
    // each question is represented as a separate paragraph
    // and all such paragraphs are delimited by '---'
    const paragraphs = strData.split(/^---$/m);

    return paragraphs.map(p => {
      const contentAndQuestions = p.split(/^ODPOWIEDZI$/m);
      return {
        content: contentAndQuestions[0].trim(),
        answers: contentAndQuestions[1]
          .split(/^ODP\./m)
          .filter(a => a && a !== '\n')
          .map(a => {
            const isCorrect = a.trim().startsWith('>>>');
            return {
              content: isCorrect ? a.replace('>>>', '').trim() : a.trim(),
              correct: isCorrect
            }
          })
      }
    })
  }

  private buildQuizStrData(questions: Question[]): string {
    const answersToString = (answers: Answer[]): string => {
      return answers.map(a => 'ODP.' + (a.correct ? ' >>> ' : ' ') + a.content).join('\n');
    };

    const paragraphs = questions.map(
      q => q.content + '\nODPOWIEDZI\n' + answersToString(q.answers));

    return paragraphs.join('\n---\n');
  }
}
