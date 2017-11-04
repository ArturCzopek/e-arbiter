import {Component, Input, OnChanges, SimpleChanges, ViewChild} from '@angular/core';
import {TaskPreview} from '../../../shared/interface/task-preview.interface';
import * as _ from 'lodash';
import {SemanticModalComponent} from 'ng-semantic';
import {CodeSubmitForm} from '../../../shared/interface/code-submit-form.interface';
import {TaskService} from '../service/task.service';

@Component({
  selector: 'arb-tour-details-task-prev',
  template: `
    <div class="tournament-details-card__tasks-panel__tasks-list__task">
      <sm-accordion>
        <sm-accordion-item>
          <accordion-title>
            <i class="icon" [ngClass]="isQuizTask() ? 'help' : 'code outline'"></i>
            {{taskPreview.name}}
          </accordion-title>
          <accordion-content>
            <p>{{taskPreview.description}}</p>
            <div *ngIf="canSeeTaskFooter" class="accordion-content-footer">
              <p>Podejścia: {{taskPreview.taskUserDetails.userAttempts}}/{{convertAttempts()}}</p>
              <p>Punkty: {{taskPreview.taskUserDetails.earnedPoints}}/{{taskPreview.maxPoints}}</p>
              <button
                [ngClass]="canExecuteTask() ? 'ui button medium teal' : 'ui button medium teal disabled'"
                (click)="isQuizTask() ? quizUploadModal.show() : codeUploadModal.show()"
              >
                <i class="terminal icon"></i>
                Wykonaj zadanie
              </button>
            </div>
          </accordion-content>
        </sm-accordion-item>
      </sm-accordion>
    </div>

    <sm-modal title="Prześlij kod" class="" icon="file text outline" #codeUploadModal>
      <modal-content>
        <form class="ui form">
          <div class="field">
            <textarea name="program" [(ngModel)]="codeSubmitForm.program" placeholder="Rozwiązanie..."></textarea>
          </div>
        </form>
      </modal-content>
      <modal-actions>
        <div class="ui buttons">
          <button class="ui button primary" (click)="codeUploadModal.hide()">Anuluj</button>
          <button (click)="executeTask()" class="ui teal medium button">Prześlij</button>
        </div>
      </modal-actions>
    </sm-modal>
    
    <arb-tour-details-quiz-upload #quizUploadModal></arb-tour-details-quiz-upload>

  `
})
export class TournamentDetailsTaskPreviewComponent implements OnChanges {

  @ViewChild('codeUploadModal') codeUploadModal: SemanticModalComponent;

  @Input() taskPreview: TaskPreview;
  @Input() status: string;
  @Input() canSeeTaskFooter: boolean;

  codeSubmitForm: CodeSubmitForm = {
    tournamentId: '',
    taskId: '',
    program: '',
    language: 'C11'
  };

  constructor(private taskService: TaskService) {
  }

  ngOnChanges(changes: SimpleChanges) {
    const newTaskPreview: TaskPreview = changes.taskPreview && changes.taskPreview.currentValue;

    if (newTaskPreview) {
      this.codeSubmitForm.tournamentId = newTaskPreview.tournamentId;
      if (newTaskPreview.taskUserDetails) {
        this.codeSubmitForm.taskId = newTaskPreview.taskUserDetails.taskId;
      }
    }
  }

  public isQuizTask() {
    return this.taskPreview && this.taskPreview.type.toUpperCase() === 'QUIZ';
  }

  public convertAttempts(): string {
    let maxAttempts: any = '';

    if (this.taskPreview && this.taskPreview.taskUserDetails) {
      maxAttempts = this.taskPreview.taskUserDetails.maxAttempts;
    }

    return `${(_.isNumber(maxAttempts)) ? maxAttempts : '-' }`;
  }

  public canExecuteTask(): boolean {

    if (this.status === 'FINISHED') { // tasks cannot be executed if tournament is finished
      return false;
    }

    const {maxAttempts, userAttempts} = this.taskPreview.taskUserDetails;

    if (!_.isNumber(maxAttempts)) {  // no number, probably null -> inf amount of execution
      return true;
    }

    return userAttempts < maxAttempts;
  }

  public executeTask() {
    this.taskService.submitCode(this.codeSubmitForm);
  }
}
