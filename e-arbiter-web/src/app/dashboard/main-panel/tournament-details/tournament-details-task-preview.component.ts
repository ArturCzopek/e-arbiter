import {Component, Input} from '@angular/core';
import {TaskPreview} from '../../../shared/interface/task-preview.interface';
import * as _ from 'lodash';

@Component({
  selector: 'arb-tour-details-task-prev',
  template: `
    <div class="tournament-details-card__tasks-panel__tasks-list__task">
      <sm-accordion>
        <sm-accordion-item>
          <accordion-title>
            {{taskPreview.name}}
          </accordion-title>
          <accordion-content>
            <p>{{taskPreview.description}}</p>
            <div *ngIf="canSeeTaskFooter" class="accordion-content-footer">
              <p>Podejścia: {{taskPreview.taskUserDetails.userAttempts}}/{{convertAttempts()}}</p>
              <p>Punkty: {{taskPreview.taskUserDetails.earnedPoints}}/{{taskPreview.maxPoints}}</p>
              <button
                [ngClass]="canExecuteTask() ? 'ui button medium teal' : 'ui button medium teal disabled'"
                (click)="executeTask()"
              >
                <i class="terminal icon"></i>
                Wykonaj zadanie
              </button>
            </div>
          </accordion-content>
        </sm-accordion-item>
      </sm-accordion>
    </div>

  `
})
export class TournamentDetailsTaskPreviewComponent {
  @Input() taskPreview: TaskPreview;
  @Input() status: string;
  @Input() canSeeTaskFooter: boolean;

  constructor() {
  }

  public convertAttempts(): string {
    const {maxAttempts} = this.taskPreview.taskUserDetails;
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
    console.log('Task to execute', this.taskPreview.name)
  }
}
