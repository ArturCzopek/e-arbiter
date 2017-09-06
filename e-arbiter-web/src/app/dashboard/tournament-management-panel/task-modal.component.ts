import {Component, ElementRef, Input, Renderer2, ViewChild} from "@angular/core";
import TaskModel, {Task} from "app/dashboard/tournament-management-panel/interface/task.interface";
import {FormArray, FormBuilder, FormGroup} from "@angular/forms";
import {SemanticModalComponent} from "ng-semantic";
import * as _ from "lodash";
import {TaskParser} from "./task-parsers/task-parser";

@Component({
  selector: 'arb-task-modal',
  template: `
    <sm-modal title="Dodaj zadanie" #innerTaskModal>
      <modal-content *ngIf="task">
        <form #form="ngForm" class="ui form">
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
          <div class="field" #taskData [ngClass]="{ 'error' : strData.invalid && strData.touched }">
            <label>{{ task.type === taskTypes[0].value ? 'Dane testowe' : 'Pytania testowe' }}<i class="help circle icon" (click)="myPopup.show($event, {position: 'right center', on: 'hover'})"></i></label>
            <textarea rows="5" name="taskData" [(ngModel)]="task.strData" #strData="ngModel" required></textarea>
            <div
              *ngIf="strData.invalid && strData.touched"
              class="ui basic red pointing prompt label"
            >Pole wymagane.</div>
          </div>
          <sm-popup #myPopup>
            <div class="ui fluid card">
              <div class="content">
                <div class="header">Pomoc</div>
                <div class="meta">Definiowanie zadania</div>
                <div *ngIf="task.type === taskTypes[0].value" class="description">
                  Każda linia powinna definiować osoby przypadek testowy w formacie:<br/><br/>
                  <code>arg_1 arg_2 ... arg_n oczekiwana_wartość</code><br/><br/>
                  Jeżeli argument zawiera spacje, należy umieścić go w cudzysłowie.<br/><br/>
                  Przykład:<br/><br/>
                  <code>ala ma kota "ala ma kota"</code><br/>
                  <code>1 2 3 4 24</code><br/>
                </div>
                <div *ngIf="task.type === taskTypes[1].value" class="description">
                  Każde pytanie składa się z dwóch części: definicji treści pytania i definicji odpowiedzi.
                  Treść pytania od odpowiedzi musi oddzielać linia zawierająca pojedyncze słowo<br/><br/>
                  <code>ODPOWIEDZI</code><br/><br/> 
                  Każda odpowiedź definiowana jest w osobnej linii i zaczyna się od ciągu znaków <br/><br/>
                  <code>ODP.</code><br/><br/>
                  Odpowiedź prawidłowa zaczyna się od<br/><br/>
                  <code>ODP. >>></code><br/><br/>
                  Kolejne pytania oddzielane są linią zawierającą ciąg znaków<br/><br/>
                  <code>---</code><br/><br/>
                  Przykład:<br/><br/>
                  <code>Stolica Polski to:<br/>
                    ODPOWIEDZI<br/>
                    ODP.Kraków<br/>
                    ODP. >>>Warszawa<br/>
                    ODP.Gdańsk<br/>
                    ---<br/>
                    Miasto posiadające ponad 300 tys mieszkańców:<br/>
                    ODPOWIEDZI<br/>
                    ODP.>>>Kraków<br/>
                    ODP.>>>Warszawa<br/>
                    ODP.Kozia wólka<br/>
                  </code>
                </div>
              </div>
            </div>
          </sm-popup>
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
  @ViewChild("form") form: FormGroup;
  @ViewChild("taskData") taskData: ElementRef;

  taskTypes = TaskModel.taskTypes;
  languages = TaskModel.languages;

  constructor(private fb: FormBuilder, private renderer: Renderer2) {}

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
      onDeny: () => this.form.reset(),
      onApprove: () => this.addToFormArray(this.task, originalTask)
    });
  }

  private addToFormArray(task: Task, originalTask?: Task) {
    if (this.form.invalid) {
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
      this.form.reset();
      return true;
    }

    this.renderer.addClass(this.taskData.nativeElement, 'error');
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
