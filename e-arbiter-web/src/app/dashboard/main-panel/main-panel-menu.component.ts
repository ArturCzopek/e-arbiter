import {AfterViewInit, Component, ElementRef, EventEmitter, Input, Output, Renderer2, ViewChild} from "@angular/core";
import {TournamentStatus} from "../../shared/interface/tournament-status.enum";

@Component({
  selector: 'arb-main-panel-menu',
  template: `
    <div class="ui secondary menu">
      <a #active class="item header__link" (click)="changeToActiveTab()">
        Aktywne
      </a>
      <a #finished class="item header__link" (click)="changeToFinishedTab()">
        Zakończone
      </a>
      <div class="right menu">
        <div class="item">
          <form (keyup.enter)="emitSearch(pageSize, query)" class="ui form no-margin-below">
            <div class="fields">
              <div class="field narrow">
                <input type="number" min="0" step="1" placeholder="ile?" [(ngModel)]="pageSize" [ngModelOptions]="{standalone: true}">
              </div>
              <div class="field broad ui icon input">
                <input type="text" placeholder="Szukaj turniejów..." [(ngModel)]="query" [ngModelOptions]="{standalone: true}">
                <i class="search link icon"></i>
              </div>
            </div>
          </form>
        </div>
      </div>
    </div>
  `
})
export class MainPanelMenuComponent implements AfterViewInit {

  @Input() tournamentsStatus: TournamentStatus;
  @Input() query: string;
  @Input() pageSize: number;

  @Output() onStatusChange: EventEmitter<TournamentStatus> = new EventEmitter();
  @Output() onSearch: EventEmitter<any> = new EventEmitter();

  @ViewChild('active') activeLink: ElementRef;
  @ViewChild('finished') finishedLink: ElementRef;


  constructor(private renderer: Renderer2) {}

  ngAfterViewInit(): void {

    const emitChange = false;

    if (this.tournamentsStatus === TournamentStatus.ACTIVE) {
      this.changeToActiveTab(emitChange);
    } else {
      this.changeToFinishedTab(emitChange);
    }
  }

  public changeToActiveTab(emitChange: boolean = true) {
    this.tournamentsStatus = TournamentStatus.ACTIVE;

    this.renderer.addClass(this.activeLink.nativeElement, 'active');
    this.renderer.removeClass(this.finishedLink.nativeElement, 'active');

    if (emitChange) {
      this.onStatusChange.emit(this.tournamentsStatus);
    }
  }

  public changeToFinishedTab(emitChange: boolean = true) {
    this.tournamentsStatus = TournamentStatus.FINISHED;

    this.renderer.addClass(this.finishedLink.nativeElement, 'active');
    this.renderer.removeClass(this.activeLink.nativeElement, 'active');

    if (emitChange) {
      this.onStatusChange.emit(this.tournamentsStatus);
    }
  }

  public emitSearch(pageSize: number, query: string) {
    this.onSearch.emit({pageSize, query})
  }
}
