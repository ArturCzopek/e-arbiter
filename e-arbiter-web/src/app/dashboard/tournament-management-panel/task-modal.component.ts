import {Component, Input, ViewChild} from "@angular/core";
import TaskModel, {Task} from "app/dashboard/tournament-management-panel/interface/task.interface";
import {FormArray, FormBuilder} from "@angular/forms";
import {SemanticModalComponent} from "ng-semantic";
import {TaskService} from "./task.service";
import * as _ from "lodash";

@Component({
  selector: 'arb-task-modal',
  template: `
    <sm-modal title="Dodaj zadanie" #innerTaskModal>
      <modal-content *ngIf="task">
        <form #f="ngForm" class="ui form">
          <div class="two fields">
            <div class="field">
              <label>Nazwa</label>
              <input type="text" name="name" [(ngModel)]="task.name">
            </div>
            <div class="field">
              <label>Typ</label>
              <select name="type" [(ngModel)]="task.type">
                <option *ngFor="let type of taskTypes" [value]="type.value">{{ type.display }}</option>
              </select>
            </div>
          </div>
          <div class="field">
            <label>Opis</label>
            <textarea rows="3" name="description" [(ngModel)]="task.description"></textarea>
          </div>
          <div *ngIf="task.type === 'CodeTask'" class="two fields">
            <div class="field">
              <label>Jezyki programowania</label>
              <sm-select 
                placeholder="Wybierz..."
                class="fluid search multiple"
                (onChange)="task.languages = $event"
              >
                <option *ngFor="let language of languages" [value]="language">{{ language }}</option>
              </sm-select>
            </div>
            <div class="field">
              <label>Timeout (ms)</label>
              <input type="number" name="timeoutInMs" [(ngModel)]="task.timeoutInMs"/>
            </div>
          </div>
          <div class="field">
            <label>{{ task.type === 'CodeTask' ? 'Dane testowe' : 'Pytania testowe' }}</label>
            <textarea rows="5" name="taskData" [(ngModel)]="task.strData"></textarea>
          </div>
        </form>
      </modal-content>
      <modal-actions>
        <div class="ui buttons">
          <div class="ui cancel button">Odrzuć</div>
          <div class="ui ok button primary">Zapisz</div>
        </div>
      </modal-actions>
    </sm-modal>
  `,
  providers: [TaskService]
})
export class TaskModalComponent {

  @Input() tasks: FormArray;

  @ViewChild("innerTaskModal") innerTaskModal: SemanticModalComponent;
  task: Task;

  taskTypes = TaskModel.taskTypes;
  languages = TaskModel.languages;

  constructor(private fb: FormBuilder, private taskService: TaskService) {}

  public addTask() {
    this.task = TaskModel.createEmptyTask();
    this.showModal(this.task);
  }

  public editTask(task: Task) {
    this.constituteTask(task);

    this.task = { ...task };
    this.showModal(this.task, task);
  }

  private showModal(task: Task, originalTask?: Task) {
    this.innerTaskModal.show({
      closable: false,
      observeChanges: true,
      onApprove: () => this.addToFormArray(this.task, originalTask)
    });
  }

  private addToFormArray(task: Task, originalTask?: Task) {
    if (this.constituteTask(task)) {
      if (originalTask) {
        _.assign(originalTask, task);
      } else {
        this.tasks.push(
          this.fb.group({
            type: [task.type],
            name: [task.name],
            description: [task.description],
            codeTaskTestSets: [task.codeTaskTestSets],
            questions: [task.questions],
            timeoutInMs: [task.timeoutInMs],
            languages: [task.languages]
          })
        );
      }
    }
  }

  private constituteTask(task: Task): boolean {
    return this.taskService.constituteTask(task);
  }

}
