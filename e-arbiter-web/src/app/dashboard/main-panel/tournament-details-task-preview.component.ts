import {AfterViewInit, ChangeDetectorRef, Component, Input} from '@angular/core';
import {TaskPreview} from '../../shared/interface/task-preview.interface';
import {TournamentStatus} from '../../shared/interface/tournament-status.enum';
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
              <p>Podejścia: {{taskPreview.userAttempts}}/{{convertAttempts()}}</p>
              <p>Punkty: {{taskPreview.pointsReceived}}/{{taskPreview.maxPoints}}</p>
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
export class TournamentDetailsTaskPreviewComponent implements AfterViewInit {
  @Input() taskPreview: TaskPreview;
  @Input() status: TournamentStatus;
  @Input() canSeeTaskFooter: boolean;

  constructor(private cdr: ChangeDetectorRef) {
  }

  ngAfterViewInit(): void {
    setTimeout(() => this.cdr.detach(), 500);
  }

  public convertAttempts(): string {
    return `${(_.isNumber(this.taskPreview.maxAttempts)) ? this.taskPreview.maxAttempts : '-' }`;
  }

  public canExecuteTask(): boolean {

    if (this.status === TournamentStatus.FINISHED) { // tasks cannot be executed if tournament is finished
      return false;
    }

    const {maxAttempts, userAttempts} = this.taskPreview;

    if (!_.isNumber(maxAttempts)) {  // no number, probably null -> inf amount of execution
      return true;
    }

    return userAttempts < maxAttempts;
  }

  public executeTask() {
    console.log('Task to execute', this.taskPreview.name)
  }
}
