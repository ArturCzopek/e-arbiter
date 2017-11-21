import {Component, ElementRef, Input, Renderer2, ViewChild} from '@angular/core';
import TaskModel, {Task} from 'app/dashboard/tournament-management-panel/interface/task.interface';
import {FormArray, FormBuilder, FormGroup} from '@angular/forms';
import {SemanticModalComponent} from 'ng-semantic';
import * as _ from 'lodash';
import {TaskParser} from './task-parsers/task.parser';

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
            <label>
              {{ task.type === taskTypes[0].value ? 'Dane testowe' : 'Pytania testowe' }}
              <i class="help circle icon" (click)="myPopup.show($event, {position: 'right center', on: 'hover', observeChanges: true})"></i>
            </label>
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
                  Każde zadanie typu quiz składa się z pytań oraz maksymalnej ilości wykonań danego zadania przez użytkownika.
                  Maksymalna ilość prób użytkownika powinna być zdefiniowana w pierwszej linii poprzez wpisanie słowa <code>PROBY</code> (bez polskich znaków znaki),
                  A następnie po znaku spacji powinna być wpisana ilość prób większa od 0.
                  W następnej linii, należy wpisać ciąg znaków <code>---</code> (3 pauzy).<br/>
                  Przykładowo, gdy użytkownik może wykonać w zadaniu tylko jedną próbę:<br/><br/>
                  <code>
                    PROBY 1<br/>
                    ---<br/>
                  </code><br/>
                  W nastepnych liniach należy zdefiniować pytania dla zadania.
                  Każde pytanie składa się z dwóch części: definicji treści pytania i definicji odpowiedzi.
                  Treść pytania od odpowiedzi musi oddzielać linia zawierająca pojedyncze słowo <code>ODPOWIEDZI</code>
                  Każda odpowiedź definiowana jest w osobnej linii i zaczyna się od ciągu znaków <code>ODP.</code>.
                  Odpowiedź prawidłowa zaczyna się od <code>ODP. >>></code>.
                  Kolejne pytania oddzielane są linią zawierającą ciąg znaków <code>---</code> (3 pauzy).<br/>
                  Przykład:<br/><br/>
                  <code>
                    PROBY 3<br/>
                    ---<br/>
                    Stolica Polski to:<br/>
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

  @ViewChild('innerTaskModal') innerTaskModal: SemanticModalComponent;
  @ViewChild('form') form: FormGroup;
  @ViewChild('taskData') taskData: ElementRef;

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
            languages: [task.languages],
            maxAttempts: [task.maxAttempts]
          })
        );
      }
      this.form.reset();
      this.renderer.removeClass(this.taskData.nativeElement, 'error');
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
        task = taskParser.parseStringDataFromTaskToTask(task);
      } else {
        task.strData = taskParser.parseTaskToString(task);
      }
    } catch (err) {
      return false;
    }

    return true
  }

}
