import {Component, Input, ViewChild} from "@angular/core";
import TaskModel, {Task} from "app/dashboard/tournament-management-panel/interface/task.interface";
import {FormArray, FormBuilder, FormGroup} from "@angular/forms";
import {SemanticModalComponent} from "ng-semantic";
import * as _ from "lodash";
import {TaskParser} from "./task-parsers/task-parser";

declare var $: any;

@Component({
  selector: 'arb-task-modal',
  template: `
    <sm-modal title="Dodaj zadanie" #innerTaskModal>
      <modal-content *ngIf="task">
        <form #f="ngForm" class="ui form">
          <div class="two fields">
            <div class="field" [ngClass]="{ 'error' : name.invalid && name.touched }">
              <label>Nazwa</label>
              <input type="text" name="name" [(ngModel)]="task.name" #name="ngModel" required minlength="3" maxlength="64">
              <div
                *ngIf="name.invalid && name.touched"
                class="ui basic red pointing prompt label"
              >Nazwa turnieju powinna być długości od 3 do 64 znaków.</div>
            </div>
            <div class="field">
              <label>Typ</label>
              <select name="type" [(ngModel)]="task.type">
                <option *ngFor="let type of taskTypes" [value]="type.value">{{ type.display }}</option>
              </select>
            </div>
          </div>
          <div class="field" [ngClass]="{ 'error' : description.invalid && description.touched }">
            <label>Opis</label>
            <textarea rows="3" name="description" [(ngModel)]="task.description" #description="ngModel" required></textarea>
            <div
              *ngIf="description.invalid && description.touched"
              class="ui basic red pointing prompt label"
            >Opis zadania jest wymagany.</div>
          </div>
          <div *ngIf="task.type === taskTypes[0].value" class="two fields">
            <div class="field">
              <label>Jezyki programowania</label>
              <sm-select
                placeholder="Wybierz..."
                [model]="task.languages[0]"
                class="fluid search multiple disabled"
              >
                <option *ngFor="let language of languages" [value]="language">{{ language }}</option>
              </sm-select>
            </div>
            <div class="field" [ngClass]="{ 'error' : timeoutInMs.invalid && timeoutInMs.touched }">
              <label>Timeout (ms)</label>
              <input type="number" name="timeoutInMs" [(ngModel)]="task.timeoutInMs" #timeoutInMs="ngModel" required/>
              <div
                *ngIf="timeoutInMs.invalid && timeoutInMs.touched"
                class="ui basic red pointing prompt label"
              >Timeout jest wymagany.</div>
            </div>
          </div>
          <div class="field task-data" [ngClass]="{ 'error' : strData.invalid && strData.touched }">
            <label>{{ task.type === taskTypes[0].value ? 'Dane testowe' : 'Pytania testowe' }}</label>
            <textarea rows="5" name="taskData" [(ngModel)]="task.strData" #strData="ngModel" required></textarea>
            <div
              *ngIf="strData.invalid && strData.touched"
              class="ui basic red pointing prompt label"
            >Pole wymagane.</div>
          </div>
        </form>
      </modal-content>
      <modal-actions>
        <div class="ui buttons">
          <button class="ui cancel button">Odrzuć</button>
          <button type="submit" class="ui ok teal button">Zapisz</button>
        </div>
      </modal-actions>
    </sm-modal>
  `
})
export class TaskModalComponent {

  @Input() tasks: FormArray;
  task: Task;

  @ViewChild("innerTaskModal") innerTaskModal: SemanticModalComponent;
  @ViewChild("f") f: FormGroup;

  taskTypes = TaskModel.taskTypes;
  languages = TaskModel.languages;

  constructor(private fb: FormBuilder) {}

  public addTask() {
    this.task = TaskModel.createEmptyTask();
    this.showModal(this.task);
  }

  public editTask(originalTask: Task) {
    this.constituteTask(originalTask);

    this.task = { ...originalTask };
    this.showModal(this.task, originalTask);
  }

  private showModal(task: Task, originalTask?: Task) {
    this.innerTaskModal.show({
      closable: false,
      observeChanges: true,
      onDeny: () => this.f.reset(),
      onApprove: () => this.addToFormArray(this.task, originalTask)
    });
  }

  private addToFormArray(task: Task, originalTask?: Task) {
    if (this.f.invalid) {
      return false;
    }

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
      this.f.reset();
      return true;
    }

    $('.task-data').attr('class', 'field error');
    return false;
  }

  private constituteTask(task: Task): boolean {
    const taskParser: TaskParser =
      this.taskTypes.find(tt => tt.value === task.type).parser;

    try {
      if (task.strData) {
        taskParser.parseStateFromStrData(task);
      } else {
        taskParser.buildStrDataFromState(task);
      }
    } catch (err) {
      return false;
    }

    return true
  }

}
