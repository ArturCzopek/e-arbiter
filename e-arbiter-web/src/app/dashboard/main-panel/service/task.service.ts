import {Injectable} from "@angular/core";
import {AuthService} from "../../../shared/service/auth.service";
import {Http} from "@angular/http";
import {CodeSubmitForm} from "../../../shared/interface/code-submit-form.interface";
import {environment} from "environments/environment";
import {ModalService} from "../../../shared/service/modal.service";

@Injectable()
export class TaskService {

  constructor(private http: Http, private authService: AuthService, private modalService: ModalService) {}

  public submitCode(codeSubmitForm: CodeSubmitForm) {
    this.http.post(`${environment.server.api.url}/tournament/api/task/submit`, codeSubmitForm,
      this.authService.prepareAuthOptions())
      .map(res => res.json())
      .first()
      .subscribe(
        data => this.modalService.showAlert(data.output),
        err => this.modalService.showAlert('Błąd wykonania.')
      );
  }

}
