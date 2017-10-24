import {Http} from '@angular/http';
import {Injectable} from '@angular/core';
import {Observable} from 'rxjs/Observable';
import {AdminServiceData} from '../interface/admin-service-data.interface';
import {AuthService} from '../../../shared/service/auth.service';
import {environment} from '../../../../environments/environment';

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
}
