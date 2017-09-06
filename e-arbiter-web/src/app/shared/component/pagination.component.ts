import {Component, EventEmitter, Input, OnChanges, OnInit, Output, SimpleChanges} from "@angular/core";
import {EMPTY_PAGE, Page} from "../interface/page.interface";
import * as _ from "lodash";

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
  private offset = 2;
  private maxToDisplay = 1 + 2 * this.offset; // current page + left + right

  ngOnInit(): void {
    this.currentPage = this.page.number + 1 || 0;   // in case of invalid page object
    this.pagesToDisplay = this.calculatePagesToDisplay();
  }

  ngOnChanges(changes: SimpleChanges): void {
    this.currentPage = this.page.number + 1 || 0;   // in case of invalid page object
    this.pagesToDisplay = this.calculatePagesToDisplay();
  }

  public trackByPageNr(index: number, pageNr: number) {
    return pageNr;
  }

  public goToPage(pageNr: number) {
    if (pageNr != this.currentPage && pageNr >= 1 && pageNr <= this.page.totalPages) {
      this.onPageChange.emit(pageNr);
    }
  }

  private calculatePagesToDisplay(): number[] {

    if (this.page === EMPTY_PAGE) {
      return [];
    }

    // display all what you can
    if (this.page.totalPages <= this.maxToDisplay) {
      return _.range(1, this.page.totalPages + 1);
    }

    let leftSide = [];

    // display half to the left or first n < offset possibles
    if (!this.page.first) {
      leftSide = (this.currentPage > this.offset) ? _.range(this.currentPage - this.offset, this.currentPage) : _.range(1, this.currentPage);
    }

    // how many elements can we add?
    const elToFillToRight =  (this.maxToDisplay - 1 - leftSide.length < this.page.totalPages - this.currentPage) ? this.maxToDisplay - 1 - leftSide.length : this.page.totalPages - this.currentPage;
    let rightSide = _.range(this.currentPage + 1, this.currentPage + 1 + elToFillToRight);

    // not proper filled array
    if (leftSide.length + 1 + rightSide.length !== this.maxToDisplay) {
      // we have some space so we should fill left side by it (right would have already been if it would be possible)
      const elToFillToLeft = this.maxToDisplay - leftSide.length - 1 - rightSide.length;
      leftSide = [..._.range(leftSide[0] - elToFillToLeft, leftSide[0]), ...leftSide];
    }
    return [...leftSide, this.currentPage, ...rightSide];
  }
}
