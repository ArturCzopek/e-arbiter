import {Component, Input} from '@angular/core';
import {ReportService} from '../service/report.service';
import {TournamentDetails} from '../interface/tournament-details.interface';
import {ModalService} from '../../../shared/service/modal.service';

@Component({
  selector: 'arb-tour-details-manage-finished',
  template: `
    <div>
      <div class="tournament-details-card__subtitle">
        <h4><i class="download icon"></i>Generuj raport</h4>
      </div>
      <div class="ui buttons">
        <button (click)="generatePdfReport()" class="ui large red button">
          <i class="file pdf outline icon"></i>
          PDF
        </button>
        <div class="or" data-text="lub"></div>
        <button (click)="generateXlsxReport()" class="ui large teal button">
          <i class="file excel outline icon"></i>
          Excel
        </button>
      </div>
    </div>
  `
})
export class TournamentDetailsManageFinishedComponent {

  @Input() tournamentDetails: TournamentDetails;

  constructor(private reportService: ReportService, private modalService: ModalService) {
  }

  generateXlsxReport() {
    this.reportService.getXlsxReport(this.tournamentDetails.id)
      .subscribe(
        file => this.reportService.downloadXlsx(file, this.tournamentDetails.name.replace(' ', '-')),
        error => this.modalService.showAlert('Nie udało się wygenerować raportu XLSX.')
      );
  }

  generatePdfReport() {
    this.reportService.getPdfReport(this.tournamentDetails.id)
      .subscribe(
        file => this.reportService.downloadPdf(file, this.tournamentDetails.name.replace(' ', '-')),
        error => this.modalService.showAlert('Nie udało się wygenerować raportu PDF.')
      );
  }
}
