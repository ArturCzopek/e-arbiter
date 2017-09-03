import {FormBuilder, FormGroup} from "@angular/forms";
import {Component, OnInit, ViewChild} from "@angular/core";
import {Translations} from "../../shared/model/calendar.model";
import {Tournament} from "./interface/tournament.interface";
import {TaskModalComponent} from "./task-modal.component";
import {TournamentService} from "./tournament.service";
import {TournamentStatus} from "../../shared/interface/tournament-status.enum";
import {ModalService} from "../../shared/service/modal.service";

declare var $: any;

@Component({
  selector: 'arb-tournament-form',
  template: `
    <form [formGroup]="myForm" class="ui form" (ngSubmit)="save(myForm.value)">
      <h2 class="ui dividing header">Tworzenie Turnieju - Szablon</h2>
      <div class="two fields">
        <div class="field">
          <label>Tytuł</label>
          <input type="text" formControlName="name"/>
        </div>
        <div class="field">
          <label>Do kiedy</label>
          <div class="ui calendar" id="calendar">
            <div class="ui input left icon">
              <i class="calendar icon"></i>
              <input type="text" formControlName="endDate">
            </div>
          </div>
        </div>
      </div>
      <div class="field">
        <label>Opis</label>
        <textarea rows="3" formControlName="description"></textarea>
      </div>
      <div class="pull-right inline fields">
        <div class="field">
          <div class="ui radio checkbox">
            <input type="radio" name="publicFlag" formControlName="publicFlag"
                   [checked]="myForm.controls['publicFlag'].value" [value]="true">
            <label>Publiczny</label>
          </div>
        </div>
        <div class="field">
          <div class="ui radio checkbox">
            <input type="radio" name="publicFlag" formControlName="publicFlag"
                   [checked]="!myForm.controls['publicFlag'].value" [value]="false">
            <label>Prywatny</label>
          </div>
        </div>
      </div>
      <div class="pull-right inline fields">
        <div class="ui checkbox">
          <input type="checkbox" formControlName="resultsVisibleForJoinedUsers"
                 [checked]="myForm.controls['resultsVisibleForJoinedUsers'].value">
          <label>Wyniki dostępne dla uczestników</label>
        </div>
      </div>
      <div *ngIf="myForm.controls['tasks']['controls'].length > 0" class="four fields">
        <div class="field">
          <label>Zadania</label>
          <div class="ui middle aligned divided list">
            <div *ngFor="let taskControl of myForm.controls['tasks']['controls']; let i = index" class="item task-item">
              <div class="right floated content">
                <i class="minus icon" (click)="myForm.controls['tasks'].removeAt(i)"></i>
                <i class="edit icon" (click)="taskModal.editTask(taskControl.value)"></i>
              </div>
              <div class="content">
                <div>{{ taskControl.value.name }}</div>
              </div>
            </div>
          </div>
        </div>
      </div>
      <div class="pull-right inline fields">
        <div class="field">
          <button class="ui teal button" type="button" (click)="taskModal.addTask()">Dodaj zadanie</button>
        </div>
      </div>
      <div class="pull-center inline fields" *ngIf="!myForm.controls['publicFlag'].value">
        <div class="field">
          <label>Hasło</label>
          <input [type]="showPassword ? 'text' : 'password'" formControlName="password"/>
        </div>
        <div class="ui checkbox">
          <input type="checkbox" [checked]="showPassword" (change)="showPassword = !showPassword"/>
          <label>Pokaż</label>
        </div>
      </div>
      <div class="button-container space-above-40">
        <button class="ui teal button huge" type="submit">Utwórz</button>
      </div>
    </form>
    
    <!-- MODAL -->
    <arb-task-modal #taskModal [tasks]="myForm.controls['tasks']"></arb-task-modal>
  `
})
export class TournamentFormComponent implements OnInit {
  myForm: FormGroup;
  showPassword: boolean;

  @ViewChild("taskModal") taskModal: TaskModalComponent;

  constructor(private fb: FormBuilder,
              private tournamentService: TournamentService,
              private modalService: ModalService) {
  }

  ngOnInit() {
    $('#calendar').calendar({
      ampm: false,
      text: {
        days: Translations.DAYS,
        months: Translations.MONTHS,
        monthsShort: Translations.MONTHS_SHORT,
        today: Translations.TODAY,
        now: Translations.NOW
      },
      onChange: (date: Date) => {
        this.myForm.controls.endDate.setValue(date.toISOString().replace('Z', ''));
      }
    });

    this.myForm = this.fb.group({
      name: [''],
      endDate: [''],
      description: [''],
      publicFlag: [true],
      resultsVisibleForJoinedUsers: [false],
      password: [''],
      status: [TournamentStatus.DRAFT],
      tasks: this.fb.array([])
    });
  }

  save(tournament: Tournament) {
    const ms = this.modalService;

    // TODO: as part of validation, handle error responses in a better way
    // remember that validation alerts should be in polish there
    this.tournamentService.saveTournament(tournament)
      .subscribe(
        (data) => ms.showAlert('Adding successful.'),
        (err) => ms.showAlert('Couldn\'t add tournament.')
      );
  }
}
