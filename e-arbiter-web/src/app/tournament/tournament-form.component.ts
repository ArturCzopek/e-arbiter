import {FormBuilder, FormGroup} from "@angular/forms";
import {Component, OnInit} from "@angular/core";
import {Tournament} from "./interfaces/tournament.interface";

declare var $: any;

@Component({
  selector: 'tournament-form',
  template: `
    <form [formGroup]="myForm" class="ui form" (ngSubmit)="save(myForm.value)">
      <h4 class="ui dividing header">Tworzenie Turnieju - Szablon</h4>
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
      <div class="ui center aligned segment">
        <button class="ui button" type="submit">Utwórz</button>
      </div>
    </form>
  `,
  styleUrls: ['./tournament-form.scss']
})
export class TournamentFormComponent implements OnInit {
  public myForm: FormGroup;

  constructor(private fb: FormBuilder) {}

  ngOnInit() {
    $('#calendar').calendar({
      onChange: (date) => {
        this.myForm.controls.endDate.setValue(date);
      }
    });

    this.myForm = this.fb.group({
      name: [''],
      endDate: [''],
      description: [''],
      publicFlag: [true],
      resultsVisibleForJoinedUsers: [false]
    });
  }

  private save(tournament: Tournament) {
    console.log(tournament);
  }
}
