import {Component} from '@angular/core';
import {AdminMail} from './model/admin-mail.model';
import {AdminService} from './service/admin.service';

@Component({
  selector: 'arb-admin-panel-mail-card',
  template: `
    <h4>Wyślij maila do wszystkich użytkowników</h4>
    <form class="ui form">
      <div class="field">
        <label>Tytuł</label>
        <input type="text" name="title" placeholder="Wprowadź tytuł..." [(ngModel)]="adminMail.subject">
      </div>
      <div class="field">
        <label>Wiadomość</label>
        <textarea name="message" placeholder="Wprowadź wiadomość..." [(ngModel)]="adminMail.message"></textarea>
      </div>
    </form>
    <div class="main-button-container">
      <button class="ui teal huge button" type="button" (click)="sendEmail()">
        <i class="mail icon"></i>
        Wyślij maila
      </button>
    </div>
    <div class="ui red message" *ngIf="errorMessage.length > 0">
      {{errorMessage}}
    </div>
    <div class="ui green message" *ngIf="successMessage.length > 0">
      {{successMessage}}
    </div>
  `
})
export class AdminPanelMailCardComponent {
  public adminMail = new AdminMail('', '');
  public successMessage = '';
  public errorMessage = '';

  constructor(private adminService: AdminService) {
  }

  public sendEmail() {
    this.successMessage = '';
    this.errorMessage = '';

    this.adminService.sendEmail(this.adminMail)
      .first()
      .subscribe(
        success => this.successMessage = `Wiadomość z tytułem ${this.adminMail.subject} jest właśnie wysyłana do użytkowników`,
        error => this.errorMessage = `Nie udało się zlecić maila z tytułem ${this.adminMail.subject} do wysłania. Sprawdź logi w celu uzyskania informacji`,
      )
  }
}
