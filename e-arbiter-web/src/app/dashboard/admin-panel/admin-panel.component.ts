import {Component} from '@angular/core';
import {MenuElement} from '../../shared/model/menu-element.model';
import {AdminServiceData} from './interface/admin-service-data.interface';
import {AdminService} from './service/admin.service';

@Component({
  selector: 'arb-admin-panel',
  template: `
    <div class="ui container center aligned scrollable-page-view">
      <arb-menu
        [menuElements]="menuElements"
        [querySearchEnabled]="false"
        [pageSizeSearchEnabled]="false"
        (onTabChange)="onTabChange($event)"></arb-menu>
        <arb-admin-panel-services-details-card
          *ngFor="let serviceDetails of servicesDetails; trackBy: trackByServiceName"
          [serviceDetails]="serviceDetails"
        ></arb-admin-panel-services-details-card>
    </div>`
})
export class AdminPanelComponent {
  public menuElements: MenuElement[] = [
    new MenuElement('Użytkownicy', 'Użytkownicy'),
    new MenuElement('Serwisy', 'Serwisy'),
    new MenuElement('Mail', 'Mail')
  ];

  public servicesDetails: (AdminServiceData | any)[] = [];
  public modulesPathsToDisplay = ['', '/auth', '/exec', '/tournament', '/results', '/monitoring', '/config']; // empty for api gateway
  public selectedTab: string = this.menuElements[0].value;

  constructor(private adminService: AdminService) {
  }

  public onTabChange(tabName: string) {
    this.selectedTab = tabName;
    this.servicesDetails = [];

    if (this.selectedTab === 'Serwisy') {
      console.log('reset');
      this.modulesPathsToDisplay.forEach(modulePath => {
        this.adminService.getServiceData(modulePath)
          .first()
          .subscribe(
            data => this.servicesDetails.push({...data, modulePath}),
            error => this.servicesDetails.push({serviceName: modulePath, health: 'NOT-OK'})
          );
      });
    }
  }

  public trackByServiceName(index: number, serviceDetails: AdminServiceData | any) {
    return (serviceDetails as AdminServiceData).serviceName;
  }
}
