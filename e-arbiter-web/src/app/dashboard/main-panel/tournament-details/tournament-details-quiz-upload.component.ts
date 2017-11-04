import {Component, ViewChild} from "@angular/core";
import {SemanticModalComponent} from "ng-semantic";
import {TaskService} from "../service/task.service";
import {ModalService} from "../../../shared/service/modal.service";
import {Task} from "../../tournament-management-panel/interface/task.interface";
import {Question} from "../../tournament-management-panel/interface/question.interface";
import {MainPanelStream} from "../service/main-panel.stream";

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

  private quizSubmission: QuizSubmission = {
    tournamentId: '',
    taskId: '',
    questions: []
  };

  constructor(private taskService: TaskService, private modalService: ModalService,
              private mainPanelStream: MainPanelStream) {}

  public show(tournamentId: string, taskId: string) {
    this.quizSubmission.tournamentId = tournamentId;
    this.quizSubmission.taskId = taskId;

    this.taskService.getTask(tournamentId, taskId)
      .first()
      .subscribe(
        task => (this.task = task) && this.quizUploadModal.show(),
        error => this.modalService.showAlert('Nie można pobrać danych odnośnie zadania.')
      );
  }

  uploadAnswers(): void {
    this.quizSubmission.questions = this.task.questions;
    this.taskService.submitQuiz(this.quizSubmission)
      .first()
      .subscribe(
        data => {
          this.task = undefined;
          this.quizUploadModal.hide();
        },
        error => this.quizUploadModal.hide() && this.modalService.showAlert('Nie udało się wysłać quizu.'),
        () => this.mainPanelStream.callUpdateCurrentTournamentDetails()
      );
  }

}

export interface QuizSubmission {
  tournamentId: string;
  taskId: string;
  questions: Question[];
}
