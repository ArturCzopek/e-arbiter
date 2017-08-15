import {FormBuilder, FormGroup} from "@angular/forms";
import {Component, OnInit} from "@angular/core";
import {Translations} from "../../shared/model/calendar.model";
import {Tournament} from "./interface/tournament.interface";

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
      <div class="pull-right inline fields space-below-20">
        <div class="field">
          <button class="ui teal button" type="button" (click)="myModal.show()">Dodaj zadanie programistyczne</button>
        </div>
        <div class="field">
          <button class="ui teal button" type="button">Dodaj quiz</button>
        </div>
      </div>
      <div class="button-container space-above-40">
        <button class="ui teal button huge" type="submit">Utwórz</button>
      </div>
    </form>
    <sm-modal title="Hello from Modal" class="basic" #myModal>
      <modal-content>
        Hello from modal!
      </modal-content>
    </sm-modal>
  `
})
export class TournamentFormComponent implements OnInit {
  public myForm: FormGroup;
  showPassword: boolean;

  constructor(private fb: FormBuilder) {
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
      onChange: (date) => {
        this.myForm.controls.endDate.setValue(date);
      }
    });

    this.myForm = this.fb.group({
      name: [''],
      endDate: [''],
      description: [''],
      publicFlag: [true],
      resultsVisibleForJoinedUsers: [false],
      password: ['']
    });
  }

  save(tournament: Tournament) {
    console.log(tournament);
  }
}
