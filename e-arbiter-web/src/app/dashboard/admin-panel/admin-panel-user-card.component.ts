import {Component, Input, OnInit} from '@angular/core';
import {User} from '../../shared/interface/user.interface';
import {AuthService} from '../../shared/service/auth.service';
import {AdminService} from './service/admin.service';

@Component({
  selector: 'arb-admin-panel-user-card',
  template: `
    <div class="user-card">
      <div class="user-card__avatar-container">
        <img class="ui left circular image" src="{{getUserImageSrc()}}">
      </div>
      <div class="user-card__main-container">
        <div class="user-card__main-container__title">
          <a target="_blank" href="https://github.com/{{user.name}}"><h4>{{user.name}}</h4></a>
        </div>
        <div class="user-card__main-container__info">
          <p class="user-card__main-container__info__info-name">Status: </p>
          <p class="user-card__main-container__info__info-details">{{getUserStatus()}}</p>
        </div>
        <div class="user-card__main-container__info">
          <p class="user-card__main-container__info__info-name">Role: </p>
          <p class="user-card__main-container__info__info-details" *ngFor="let role of user.roles">{{role.name}}</p>
        </div>
      </div>
      <div class="user-card__buttons-container">
        <div class="ui buttons">
          <button class="ui large button" [ngClass]="adminButtonClass" type="button" (click)="toggleAdminRole()">{{adminButtonText}}</button>
          <button class="ui large button" [ngClass]="statusButtonClass" type="button" (click)="toggleStatus()">{{statusButtonText}}</button>
        </div>
      </div>
    </div>
  `
})
export class AdminPanelUserCardComponent implements OnInit {
  @Input() initUser: User;
  public user: User;
  public adminButtonText = '';
  public adminButtonClass = '';

  public statusButtonText = '';
  public statusButtonClass = '';

  constructor(private authService: AuthService, private adminService: AdminService) {

  }

  ngOnInit(): void {
    this.user = this.initUser;

    if (this.user.roles.some(role => role.name.toUpperCase() === 'ADMIN')) {
      this.setWarningAdminRoleButton();
    } else {
      this.setNormalAdminRoleButton();
    }

    if (this.user.enabled) {
      this.setWarningUserStatusButton();
    } else {
      this.setNormalUserStatusButton();
    }
  }

  public getUserImageSrc(): string {
    return this.authService.getUserImgLinkByName(this.user.name);
  }

  public toggleAdminRole(): void {
    this.adminButtonClass += ' loading';
    this.adminService.toggleAdminRole(this.user.id)
      .first()
      .subscribe(
        data => {
          this.user = data;
          if (this.user.roles.some(role => role.name.toUpperCase() === 'ADMIN')) {
            this.setWarningAdminRoleButton();
          } else {
            this.setNormalAdminRoleButton();
          }
        },
        error => this.adminButtonClass.replace(' loading', '')
      );
  }

  public toggleStatus(): void {
    this.statusButtonText += ' loading';
    this.adminService.toggleStatus(this.user.id)
      .first()
      .subscribe(
        data => {
          this.user = data;
          if (this.user.enabled) {
            this.setWarningUserStatusButton();
          } else {
            this.setNormalUserStatusButton();
          }
        },
        error => this.statusButtonClass.replace(' loading', '')
      );

  }

  public getUserStatus(): string {
    return (this.user.enabled) ? 'Aktywny' : 'Nieaktywny';
  }

  private setWarningAdminRoleButton() {
    this.adminButtonText = 'Odbierz prawa administatora';
    this.adminButtonClass = 'red';
  }

  private setNormalAdminRoleButton() {
    this.adminButtonText = 'Nadaj prawa administatora';
    this.adminButtonClass = 'teal';
  }

  private setWarningUserStatusButton() {
    this.statusButtonText = 'Zablokuj użytkownika';
    this.statusButtonClass = 'red';
  }

  private setNormalUserStatusButton() {
    this.statusButtonText = 'Odblokuj użytkownika';
    this.statusButtonClass = 'teal';
  }
}
