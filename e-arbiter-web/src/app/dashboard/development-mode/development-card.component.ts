import {Component} from '@angular/core';
import {AuthService} from '../../shared/service/auth.service';
import {Http} from '@angular/http';
import {environment} from '../../../environments/environment';
import {Observable} from 'rxjs/Observable';
import 'rxjs/add/observable/of';
import {ModalService} from '../../shared/service/modal.service';

@Component({
  selector: 'arb-dvlp',
  template: `
    <div class="ui container center aligned scrollable-page-view" *ngIf="authService.isLoggedInUser()">
      <h4>Zalogowano jako {{authService.getLoggedInUserName()}}</h4>
      <div class="ui buttons">
        <button (click)="executeBlockedInnerRequest()" class="ui red medium button">
            Uruchom inner request (powinien byc zablokowany, sorry)
        </button>
        <button (click)="getMeInfo()" class="ui teal medium button">/me (console)</button>
      </div>
      <form class="ui form">
        <div class="two fields">
          <div class="field">
            <input type="text" name="tournamentId" [(ngModel)]="submitRequest.tournamentId" placeholder="Tournament ID"/>
          </div>
          <div class="field">
            <input type="text" name="taskId" [(ngModel)]="submitRequest.taskId" placeholder="Task ID"/>
          </div>
        </div>
        <div class="field">
          <textarea name="program" [(ngModel)]="submitRequest.program" placeholder="Code Solution..."></textarea>
        </div>
        <button (click)="uploadCode()" class="ui teal medium button">/code (console)</button>
      </form>
      <h3>Testowe maile</h3>
      <div class="ui form">
        <div class="field">
          <label>Tournament Id</label>
          <input type="text" name="tournamentId" [(ngModel)]="tournamentId" placeholder="testowe tour id"/>
        </div>
      </div>
      <button (click)="getResults()" class="ui teal medium button">/results (console, id brane z inputa powyzej)</button>
      <div class="ui buttons">
        <button (click)="sendFinishedEmail()" class="ui pink medium button">Zakończony</button>
        <button (click)="sendExtendedEmail()" class="ui yellow medium button">Przedłużony</button>
        <button (click)="sendActivatedEmail()" class="ui olive medium button">Aktywowany</button>
        <button (click)="sendRemovedEmail()" class="ui violet medium button">Usunięty</button>
        <button (click)="sendJoinedEmail()" class="ui purple medium button">Dołączył</button>
      </div>
      <p>Mail Status: {{mailStatus}}</p>
      </div>`
})
export class DevelopmentCardComponent {

  tournamentId = '000000000000000000000015';
  mailStatus = '';

  submitRequest = {
    tournamentId: '',
    taskId: '',
    program: '',
    language: 'C11'
  };

  constructor(public authService: AuthService, private http: Http, private modalService: ModalService) {

  }

  public executeBlockedInnerRequest() {
    this.http
    // existing endpoint
      .get(`${environment.server.api.url}/auth/inner/user/name/1`, this.authService.prepareAuthOptions())
      .map(res => res.json())
      .catch((e) => {
        console.log(`Tak miało być, catched, blocked, not found`);
        return Observable.of(e);
      })
      .first()
      .subscribe();
  }

  public getMeInfo() {
    this.authService.getMeInfo()
      .first()
      .subscribe(
        res => console.log(res)
      )
  }

  public uploadCode() {
    this.http.post(`${environment.server.api.url}/tournament/api/task/submit`, this.submitRequest,
      this.authService.prepareAuthOptions()).map(res => res.json()).first().subscribe(data => this.modalService.showAlert(JSON.stringify(data)));
  }

  public getResults() {
    this.http.get(`${environment.server.api.url}/tournament/api/results/${this.tournamentId}`, this.authService.prepareAuthOptions())
      .map(res => res.json()).first().subscribe(data => this.modalService.showAlert(JSON.stringify(data)), data => this.modalService.showAlert(JSON.stringify(data)));
  }

  public sendFinishedEmail() {
    this.http
      .get(`${environment.server.api.url}/tournament/poc/email/finished/${this.tournamentId}`, this.authService.prepareAuthOptions())
      .first()
      .map(res => '' + res)
      .subscribe(data => this.mailStatus = data);
  }

  public sendExtendedEmail() {
    this.http
      .get(`${environment.server.api.url}/tournament/poc/email/extend/${this.tournamentId}`, this.authService.prepareAuthOptions())
      .first()
      .map(res => '' + res)
      .subscribe(data => this.mailStatus = data);
  }

  public sendActivatedEmail() {
    this.http
      .get(`${environment.server.api.url}/tournament/poc/email/activate/${this.tournamentId}`, this.authService.prepareAuthOptions())
      .first()
      .map(res => '' + res)
      .subscribe(data => this.mailStatus = data);
  }

  public sendRemovedEmail() {
    this.http
      .get(`${environment.server.api.url}/tournament/poc/email/removed/${this.tournamentId}`, this.authService.prepareAuthOptions())
      .first()
      .map(res => '' + res)
      .subscribe(data => this.mailStatus = data);
  }

  public sendJoinedEmail() {
    this.http
      .get(`${environment.server.api.url}/tournament/poc/email/joined/${this.tournamentId}`, this.authService.prepareAuthOptions())
      .first()
      .map(res => '' + res)
      .subscribe(data => this.mailStatus = data);
  }
}
