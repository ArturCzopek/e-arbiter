import {Http, ResponseContentType} from '@angular/http';
import {AuthService} from '../../../shared/service/auth.service';
import {Injectable} from '@angular/core';
import {Observable} from 'rxjs/Observable';
import {environment} from '../../../../environments/environment';
import * as FileSaver from 'file-saver';

@Injectable()
export class ReportService {
  constructor(private http: Http, private authService: AuthService) {
  }

  public getPdfReport(tournamentId: string): Observable<any> {

    const options = this.authService.prepareAuthOptions();
    options.responseType = ResponseContentType.ArrayBuffer;

    return this.http.get(
      `${environment.server.tournament.reportUrl}/pdf/${tournamentId}`,
      options
    )
      .first()
  }

  public downloadPdf(file: Blob, name: string) {
    const blob = new Blob([file], { type: 'application/pdf' });
    FileSaver.saveAs(blob, `Raport_${name}.pdf`);
  }
}
