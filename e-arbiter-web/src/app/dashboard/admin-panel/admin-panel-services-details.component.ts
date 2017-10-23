import {Component, OnInit} from '@angular/core';
import {AdminServiceData} from './interface/admin-service-data.interface';
import {AdminService} from './service/admin.service';

@Component({
  selector: 'arb-admin-panel-services-details',
  template: `
    <div class="ui container center aligned scrollable-page-view">
      <arb-admin-panel-services-details-card
        *ngFor="let serviceDetails of servicesDetails; trackBy: trackByServiceName"
        [serviceDetails]="serviceDetails"
      ></arb-admin-panel-services-details-card>
    </div>
  `
})
export class AdminPanelServicesDetailsComponent implements OnInit {

  public servicesDetails: (AdminServiceData | any)[] = [];
  private modulesPathsToDisplay = ['', '/auth', '/exec', '/tournament', '/results', '/monitoring', '/config']; // empty for api gateway

  constructor(private adminService: AdminService) {

  }

  ngOnInit(): void {
    this.modulesPathsToDisplay.forEach(modulePath => {
      this.adminService.getServiceData(modulePath)
        .first()
        .subscribe(
          data => this.servicesDetails.push(data),
          error => this.servicesDetails.push({serviceName: modulePath, health: 'NOT-OK'})
        );
    });
  }

  public trackByServiceName(index: number, serviceDetails: AdminServiceData | any) {
    return (serviceDetails as AdminServiceData).serviceName;
  }
}
