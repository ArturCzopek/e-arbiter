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
    return this.getReport(tournamentId, 'pdf');
  }

  public getXlsxReport(tournamentId: string): Observable<any> {
    return this.getReport(tournamentId, 'xlsx');
  }

  public downloadPdf(file: Blob, name: string) {
    this.downloadFile(file, name, 'application/pdf', 'pdf');
  }

  public downloadXlsx(file: Blob, name: string) {
    this.downloadFile(file, name, 'application/vnd.ms-excel', 'xlsx');
  }

  private getReport(tournamentId: string, type: string) {
    const options = this.authService.prepareAuthOptions();
    options.responseType = ResponseContentType.ArrayBuffer;

    return this.http.get(
      `${environment.server.tournament.reportUrl}/${type}/${tournamentId}`,
      options
    )
      .first()
      .map(res => res.blob());
  }

  private downloadFile(file: Blob, name: string, type: string, extension: string) {
    const blob = new Blob([file], {type});
    FileSaver.saveAs(blob, `Raport_${name}.${extension}`);
  }
}
