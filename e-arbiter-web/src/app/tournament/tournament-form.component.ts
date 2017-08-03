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
      <button type="submit">Submit</button>
    </form>
  `,
  styleUrls: ['./tournament-form.scss']
})
export class TournamentFormComponent implements OnInit {
  public myForm: FormGroup;

  constructor(private fb: FormBuilder) {}

  ngOnInit() {
    $('#calendar').calendar({
      onChange: (date, text) => console.log(date, text)
    });

    this.myForm = this.fb.group({
      name: [''],
      endDate: ['']
    });
  }

  private save(tournament: Tournament) {
    console.log(tournament);
  }
}
