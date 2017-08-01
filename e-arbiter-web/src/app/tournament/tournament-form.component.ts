import {FormBuilder, FormGroup} from "@angular/forms";
import {Component, OnInit} from "@angular/core";
import {Tournament} from "./interfaces/tournament.interface";

@Component({
  selector: 'tournament-form',
  template: `
    <form [formGroup]="myForm" class="ui form" (ngSubmit)="save(myForm.value)">
      <h4 class="ui dividing header">Tournament</h4>
      <div class="field">
        <label>Name</label>
        <input type="text" formControlName="name"/>
      </div>
    </form>
  `
})
export class TournamentFormComponent implements OnInit {
  public myForm: FormGroup;

  constructor(private fb: FormBuilder) {}

  ngOnInit() {
    this.myForm = this.fb.group({
      name: ['']
    });
  }

  private save(tournament: Tournament) {
    console.log(tournament);
  }
}
