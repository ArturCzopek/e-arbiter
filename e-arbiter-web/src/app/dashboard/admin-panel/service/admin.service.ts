import {Http} from '@angular/http';
import {Injectable} from '@angular/core';
import {Observable} from 'rxjs/Observable';
import {AdminServiceData} from '../interface/admin-service-data.interface';
import {AuthService} from '../../../shared/service/auth.service';
import {environment} from '../../../../environments/environment';
import {AdminMail} from '../model/admin-mail.model';
import {User} from '../../../shared/interface/user.interface';

@Injectable()
export class AdminService {
  constructor(private http: Http, private authService: AuthService) {

  }

  public getServiceData(serviceGatewayAddress: string): Observable<AdminServiceData> {
    return this.http.get(`${environment.server.api.url}${serviceGatewayAddress}/admin/service-data`,
      this.authService.prepareAuthOptions()
    )
      .map(res => res.json());
  }

  public getLogs(serviceGatewayAddress: string): Observable<any> {
    return this.http.get(`${environment.server.api.url}${serviceGatewayAddress}/logfile`,
      this.authService.prepareAuthOptions()
    )
      .map(res => res.text());
  }

  public getAllUsers(): Observable<User[]> {
    return this.http.get(`${environment.server.auth.adminUrl}/all`,
      this.authService.prepareAuthOptions()
    )
      .map(res => res.json());
  }

  public sendEmail(adminMail: AdminMail): Observable<any> {
    return this.http.post(`${environment.server.tournament.adminUrl}/send-email`,
      adminMail,
      this.authService.prepareAuthOptions()
    )
      .map(res => res.text());
  }

  public toggleAdminRole(userId: number): Observable<User> {
    return this.http.put(`${environment.server.auth.adminUrl}/admin-role/${userId}`,
      {},
      this.authService.prepareAuthOptions()
    )
      .map(res => res.json());
  }

  public toggleStatus(userId: number): Observable<User> {
    return this.http.put(`${environment.server.auth.adminUrl}/status/${userId}`,
      {},
      this.authService.prepareAuthOptions()
    )
      .map(res => res.json());
  }
}
