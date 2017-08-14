import {NgModule} from "@angular/core";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {TournamentFormComponent} from "./tournament-form.component";
import {CommonModule} from "@angular/common";

@NgModule({
  imports: [ ReactiveFormsModule, CommonModule, FormsModule ],
  declarations: [ TournamentFormComponent ],
  exports: [ TournamentFormComponent ]
})
export class TournamentModule {}
