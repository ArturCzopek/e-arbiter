import {Language} from "./language.enum";
import {Question} from "./question.interface";
import {CodeTaskTestSet} from "./code-task-test-set.interface";

const TaskModel = {
  taskTypes: [
    {
      value: 'CodeTask',
      display: 'Zadanie programistyczne'
    },
    {
      value: 'QuizTask',
      display: 'Quiz'
    }
  ],
  createEmptyTask: () => {
    return {
      type: TaskModel.taskTypes[0].value,
      name: '',
      description: '',
      codeTaskTestSets: [],
      questions: [],
      timeoutInMs: 0,
      languages: []
    };
  }
};

export interface Task {
  type: string;
  name: string;
  description: string;
  codeTaskTestSets?: CodeTaskTestSet[];
  languages?: Language[];
  timeoutInMs?: number;
  questions?: Question[];
}

export default TaskModel;
