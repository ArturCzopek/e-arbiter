import {FormBuilder, FormGroup} from "@angular/forms";
import {Component} from "@angular/core";

@Component({
  selector: 'tournament-form',
  template: `
    <h1>Works!</h1>
  `
})
export class TournamentFormComponent {
  public myForm: FormGroup;
  constructor(private fb: FormBuilder) {}
}
