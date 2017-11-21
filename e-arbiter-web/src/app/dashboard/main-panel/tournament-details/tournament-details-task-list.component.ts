import {Component, Input} from '@angular/core';
import {TaskPreview} from '../../../shared/interface/task-preview.interface';

@Component({
  selector: 'arb-tour-details-task-list',
  template: `
    <div class="tournament-details-card__tasks-panel">
      <div class="tournament-details-card__subtitle">
        <h4><i class="list layout icon"></i>Zadania</h4>
      </div>
      <div class="tournament-details-card__tasks-panel__tasks-list">
        <arb-tour-details-task-prev
          [taskPreview]="task"
          [status]="status"
          [canSeeTaskFooter]="canSeeTaskFooter"
          *ngFor="let task of taskPreviews;
          trackBy: trackByName"
        ></arb-tour-details-task-prev>
      </div>
    </div>
  `
})
export class TournamentDetailsTaskListComponent {
  @Input() taskPreviews: TaskPreview[];
  @Input() status: string;
  @Input() canSeeTaskFooter: boolean;

  public trackByName(index: number, task: TaskPreview): string {
    return task.name;
  }
}
