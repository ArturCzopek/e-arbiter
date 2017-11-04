import {Component, ViewChild} from "@angular/core";
import {SemanticModalComponent} from "ng-semantic";
import {TaskService} from "../service/task.service";
import {ModalService} from "../../../shared/service/modal.service";
import {Task} from "../../tournament-management-panel/interface/task.interface";

@Component({
  selector: 'arb-tour-details-quiz-upload',
  template: `
    <sm-modal title="Wykonaj quiz" icon="write square" #quizUploadModal>
      <modal-content>
        <div  *ngIf="task && task.questions" class="ui form">
          <div *ngFor="let question of task.questions; let i = index" class="grouped fields">
            <label>{{question.content}}</label>
            <div *ngFor="let answer of question.answers; let j = index" class="field">
              <div class="ui radio checkbox">
                <input type="radio" [value]="j" name="{{'question' + i}}" [(ngModel)]="question['selectedAnswer']">
                <label>{{answer.content}}</label>
              </div>
            </div>
          </div>
        </div>
      </modal-content>
      <modal-actions>
        <div class="ui buttons">
          <button class="ui button primary" (click)="quizUploadModal.hide()">Anuluj</button>
          <button class="ui teal medium button" (click)="uploadAnswers()">Prześlij</button>
        </div>
      </modal-actions>
    </sm-modal>
  `
})
export class TournamentDetailsQuizUploadComponent {

  @ViewChild('quizUploadModal') quizUploadModal: SemanticModalComponent;
  task: Task;

  private tournamentId: string;
  private taskId: string;

  constructor(private taskService: TaskService, private modalService: ModalService) {}

  public show(tournamentId: string, taskId: string) {
    this.tournamentId = tournamentId;
    this.taskId = taskId;

    this.taskService.getTask(tournamentId, taskId)
      .first()
      .subscribe(
        task => (this.task = task) && this.quizUploadModal.show(),
        error => this.modalService.showAlert('Nie można pobrać danych odnośnie zadania.')
      );
  }

  uploadAnswers(): void {
    console.log(this.tournamentId, this.taskId, this.task.questions);
    this.task = undefined;
    this.quizUploadModal.hide();
  }

}
