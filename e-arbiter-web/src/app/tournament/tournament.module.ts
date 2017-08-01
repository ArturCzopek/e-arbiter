import {NgModule} from "@angular/core";
import {ReactiveFormsModule} from "@angular/forms";
import {TournamentFormComponent} from "./tournament-form.component";

@NgModule({
  imports: [ ReactiveFormsModule ],
  declarations: [ TournamentFormComponent ],
  exports: [ TournamentFormComponent ]
})
export class TournamentModule {}
