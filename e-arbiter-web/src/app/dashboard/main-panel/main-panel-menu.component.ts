import {AfterViewInit, Component, ElementRef, EventEmitter, Input, Output, Renderer2, ViewChild} from "@angular/core";

@Component({
  selector: 'arb-main-panel-menu',
  template: `
    <div class="ui secondary menu">
      <a #active class="item header__link" (click)="changeTab('active')">
        Aktywne
      </a>
      <a #finished class="item header__link" (click)="changeTab('finished')">
        Zakończone
      </a>
      <div class="right menu">
        <div class="item">
          <form (keyup.enter)="emitSearch(tournamentsPerPage, query)" class="ui form no-margin-below">
            <div class="fields">
              <div class="field narrow">
                <input type="number" min="0" step="1" placeholder="ile?" [(ngModel)]="tournamentsPerPage" [ngModelOptions]="{standalone: true}">
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

  @Input() currentTab: string;
  @Input() query: string;
  @Input() tournamentsPerPage: number;

  @Output() onTabChange: EventEmitter<string> = new EventEmitter();
  @Output() onSearch: EventEmitter<any> = new EventEmitter();

  @ViewChild('active') activeLink: ElementRef;
  @ViewChild('finished') finishedLink: ElementRef;


  constructor(private renderer: Renderer2) {

  }


  ngAfterViewInit(): void {
    const emitChange = false;
    this.changeTab(this.currentTab, emitChange);
  }

  public changeTab(tabName: string, emitChange: boolean = true) {
    this.currentTab = tabName;

    if (this.currentTab === 'active') {
      this.renderer.addClass(this.activeLink.nativeElement, 'active');
      this.renderer.removeClass(this.finishedLink.nativeElement, 'active');
    } else {
      this.renderer.addClass(this.finishedLink.nativeElement, 'active');
      this.renderer.removeClass(this.activeLink.nativeElement, 'active');
    }

    if (emitChange) {
      this.onTabChange.emit(tabName);
    }
  }


  public emitSearch(tournamentsPerPage: number, query: string) {
    this.onSearch.emit({tournamentsPerPage, query})
  }
}
