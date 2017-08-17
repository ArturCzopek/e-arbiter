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
  languages: ['JAVA', 'PYTHON', 'C11', 'CPP'],
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
  languages?: string[];
  timeoutInMs?: number;
  questions?: Question[];
}

export default TaskModel;
