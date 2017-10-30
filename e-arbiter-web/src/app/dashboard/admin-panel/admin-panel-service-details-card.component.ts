import {Component, EventEmitter, Input, Output} from '@angular/core';
import {AdminServiceData} from './interface/admin-service-data.interface';
import {AdminService} from './service/admin.service';
import {ServiceLogs} from "./model/service-logs.model";

@Component({
  selector: 'arb-admin-panel-services-details-card',
  template: `
    <div class="service-details-card">
      <div class="service-details-card__title-container">
        <div class="service-details-card__title-container__title">
          <h4>{{serviceDetails.serviceName}}/:{{serviceDetails?.port}}</h4>
        </div>
        <div class="service-details-card__title-container__status
            service-details-card__title-container__status--{{serviceDetails.health}}">
          <h4>{{serviceDetails.health}}</h4>
        </div>
      </div>
      <div class="service-details-card__data-row-container">
        <div class="service-details-card__data-row-container__property">
          <div class="service-details-card__data-row-container__property__label">
            Wersja Javy:
          </div>
          <div class="service-details-card__data-row-container__property__value">
            {{serviceDetails.javaVersion || '-'}}
          </div>
        </div>
        <div class="service-details-card__data-row-container__property">
          <div class="service-details-card__data-row-container__property__label">
            Logi:
          </div>
          <div class="service-details-card__data-row-container__property__value">
            {{serviceDetails.logFilePath || '-'}}
          </div>
        </div>
        <div class="service-details-card__data-row-container__property">
          <div class="service-details-card__data-row-container__property__label">
            Profile:
          </div>
          <div class="service-details-card__data-row-container__property__value">
            {{serviceDetails.profiles || '-'}}
          </div>
        </div>
      </div>
      <div class="service-details-card__data-row-container">
        <div class="service-details-card__data-row-container__property">
          <div class="service-details-card__data-row-container__property__label">
            Wolna pamięć:
          </div>
          <div class="service-details-card__data-row-container__property__value">
            {{serviceDetails.memoryFree || '-'}}
          </div>
        </div>
        <div class="service-details-card__data-row-container__property">
          <div class="service-details-card__data-row-container__property__label">
            Całkowita pamięc:
          </div>
          <div class="service-details-card__data-row-container__property__value">
            {{serviceDetails.memoryTotal || '-'}}
          </div>
        </div>
        <div class="service-details-card__data-row-container__property">
          <div class="service-details-card__data-row-container__property__label">
            Maksymalna pamięć:
          </div>
          <div class="service-details-card__data-row-container__property__value">
            {{serviceDetails.memoryMax || '-'}}
          </div>
        </div>
      </div>
      <div *ngIf="serviceDetails.port" class="service-details-card__link">
        <a [attr.disabled]="!serviceDetails.port" (click)="getLogs()">Pokaż logi</a>
      </div>
    </div>
  `
})
export class AdminPanelServicesDetailsCardComponent {
  @Input() serviceDetails: AdminServiceData;
  @Output() onLogsLoaded = new EventEmitter<ServiceLogs>();

  constructor(private adminService: AdminService) {

  }

  getLogs(): void {
    this.adminService.getLogs(this.serviceDetails.modulePath)
      .first()
      .subscribe(
        logs => this.onLogsLoaded.emit(new ServiceLogs(this.serviceDetails.serviceName, logs)),
      );
  }
}
