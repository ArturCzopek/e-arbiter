import {Component, ViewChild} from "@angular/core";
import {SemanticModalComponent} from "ng-semantic";

@Component({
  selector: 'arb-tour-details-quiz-upload',
  template: `
    <sm-modal title="Wykonaj quiz" icon="write square" #quizUploadModal>
      <modal-content>
        <h1>Hello!</h1>
      </modal-content>
      <modal-actions>
        <div class="ui buttons">
          <button class="ui button primary" (click)="quizUploadModal.hide()">Anuluj</button>
          <button class="ui teal medium button">Prześlij</button>
        </div>
      </modal-actions>
    </sm-modal>
  `
})
export class TournamentDetailsQuizUploadComponent {

  @ViewChild('quizUploadModal') quizUploadModal: SemanticModalComponent;

  public show() {
    this.quizUploadModal.show();
  }

}
