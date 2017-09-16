import {Component, EventEmitter, Input, OnChanges, OnInit, Output, SimpleChanges} from '@angular/core';
import {Page} from '../interface/page.interface';
import {PageCounterService} from '../service/page-counter.service';

@Component({
  selector: 'arb-pagination',
  template: `
    <div class="ui container fixed-footer">
      <div class="ui pagination menu" *ngIf="page.totalPages > 0">
        <a
          [ngClass]="currentPage - maxToDisplay >= 1 ? 'item' : 'item disabled'"
          (click)="goToPage(currentPage - maxToDisplay)"
        >
          <<
        </a>
        <a
          [ngClass]="!page.first ? 'item' : 'item disabled'"
          (click)="goToPage(currentPage - 1)"
        >
          <
        </a>
        <a
          [ngClass]="pageNr === currentPage ? 'item active' : 'item'"
          *ngFor="let pageNr of pagesToDisplay; trackBy: trackByPageNr"
          (click)="goToPage(pageNr)"
        >
          {{pageNr}}
        </a>
        <a
          [ngClass]="!page.last ? 'item' : 'item disabled'"
          (click)="goToPage(currentPage + 1)"
        >
          >
        </a>
        <a
          [ngClass]="currentPage + maxToDisplay <= page.totalPages ? 'item' : 'item disabled'"
          (click)="goToPage(currentPage + maxToDisplay)"
        >
          >>
        </a>
      </div>
    </div>
  `
})
export class PaginationComponent implements OnInit, OnChanges {

  @Input() page: Page<any>;
  @Output() onPageChange: EventEmitter<number> = new EventEmitter();

  public pagesToDisplay: number[] = [];
  public currentPage = 1;
  public offset = 2;
  public maxToDisplay = 1 + 2 * this.offset; // current page + left + right

  constructor(private pageCounterService: PageCounterService) {}

  ngOnInit(): void {
    this.updatePagesToDisplay();
  }

  ngOnChanges(changes: SimpleChanges): void {
    this.updatePagesToDisplay();
  }

  public trackByPageNr(index: number, pageNr: number) {
    return pageNr;
  }

  public goToPage(pageNr: number) {
    if (pageNr !== this.currentPage && pageNr >= 1 && pageNr <= this.page.totalPages) {
      this.onPageChange.emit(pageNr);
    }
  }

  private updatePagesToDisplay() {
    this.currentPage = this.page.number + 1 || 0;   // in case of invalid page object
    this.pagesToDisplay = this.pageCounterService.generatePageList(this.page, this.maxToDisplay, this.offset, this.currentPage);
  }
}
