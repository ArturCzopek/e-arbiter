import {Component, OnInit, ViewChild} from '@angular/core';
import {MenuElement} from '../../shared/model/menu-element.model';
import {AdminServiceData} from './interface/admin-service-data.interface';
import {AdminService} from './service/admin.service';
import {ServiceLogs} from './model/service-logs.model';
import {SemanticModalComponent} from 'ng-semantic';
import {User} from '../../shared/interface/user.interface';
import {AuthService} from "../../shared/service/auth.service";

@Component({
  selector: 'arb-admin-panel',
  template: `
    <div class="ui container center aligned scrollable-page-view">
      <arb-menu
        [menuElements]="menuElements"
        [querySearchEnabled]="false"
        [pageSizeSearchEnabled]="false"
        (onTabChange)="onTabChange($event)"></arb-menu>
      <arb-admin-panel-user-card
        *ngFor="let user of users; trackBy: trackByUserId"
        [initUser]="user"
      ></arb-admin-panel-user-card>
      <arb-admin-panel-services-details-card
        *ngFor="let serviceDetails of servicesDetails; trackBy: trackByServiceName"
        [serviceDetails]="serviceDetails"
        (onLogsLoaded)="showLogsModal($event)"
      ></arb-admin-panel-services-details-card>
      <arb-admin-panel-mail-card *ngIf="showMail"></arb-admin-panel-mail-card>
    </div>
    <sm-modal title="{{logsModalTitle}}" #logsModal>
      <modal-content *ngIf="logs">
        <div class="code-container">
          {{logs}}
        </div>
      </modal-content>
    </sm-modal>`
})
export class AdminPanelComponent implements OnInit {
  @ViewChild('logsModal') logsModal: SemanticModalComponent;

  public menuElements: MenuElement[] = [
    new MenuElement('Użytkownicy', 'Użytkownicy'),
    new MenuElement('Serwisy', 'Serwisy'),
    new MenuElement('Mail', 'Mail')
  ];

  public servicesDetails: (AdminServiceData | any)[] = [];
  public users: User[] = [];
  public modulesPathsToDisplay = ['', '/auth', '/exec', '/tournament', '/results', '/monitoring', '/config']; // empty for api gateway
  public selectedTab: string = this.menuElements[0].value;
  public logsModalTitle = '';
  public logs = '';
  public showMail = false;

  constructor(private adminService: AdminService, private authService: AuthService) {
  }

  public ngOnInit(): void {
    this.showUsersTab();
  }

  public onTabChange(tabName: string) {
    this.selectedTab = tabName;
    this.users = [];
    this.servicesDetails = [];
    this.showMail = false;

    if (this.selectedTab === 'Użytkownicy') {
      this.showUsersTab();
    } else if (this.selectedTab === 'Mail') {
      this.showMailTab();
    } else if (this.selectedTab === 'Serwisy') {
      this.showServicesTab();
    }
  }

  public showLogsModal(serviceLogs: ServiceLogs) {
    this.logsModalTitle = `Logi - ${serviceLogs.serviceName}`;
    this.logs = serviceLogs.logs;
    this.logsModal.show();
  }

  public trackByUserId(index: number, user: User) {
    return user.id;
  }

  public trackByServiceName(index: number, serviceDetails: AdminServiceData | any) {
    return (serviceDetails as AdminServiceData).serviceName;
  }

  private showUsersTab() {
    this.adminService.getAllUsers()
      .first()
      .subscribe(
        data => this.users = data.filter(user => user.name !== this.authService.getLoggedInUserName())
      )
  }

  private showMailTab() {
    this.showMail = true;
  }

  private showServicesTab() {
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
