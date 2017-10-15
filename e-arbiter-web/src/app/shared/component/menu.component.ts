import {
  AfterViewInit, Component, ElementRef, EventEmitter, Input, OnChanges, OnDestroy, Output, Renderer2, SimpleChanges,
  ViewChild
} from '@angular/core';
import {MenuElement} from '../model/menu-element.model';
import {Subscription} from 'rxjs/Subscription';
import {Observable} from 'rxjs/Observable';
import 'rxjs/add/observable/fromEvent';
import 'rxjs/operator/filter';

@Component({
  selector: 'arb-menu',
  template: `
    <div #menu class="ui secondary menu">
      <a
        *ngFor="let element of menuElements; trackBy: trackByValue"
        (click)="changeMenuElement(element)"
        class="item header__link"
      >{{element.label}}</a>
      <div class="right menu">
        <div class="item">
          <form class="ui form no-margin-below" [ngClass]="querySearchEnabled ? '' : 'corner-tiny-form'">
            <div [ngClass]="querySearchEnabled ? 'fields' : 'one field'">
              <div class="field narrow">
                <input
                  #pageSizeInput
                  type="number"
                  min="0"
                  step="1"
                  placeholder="ile?">
              </div>
              <div *ngIf="querySearchEnabled" class="field broad ui icon input">
                <input
                  #queryInput
                  type="text"
                  placeholder="Szukaj turniejów...">
                <i class="search link icon"></i>
              </div>
            </div>
          </form>
        </div>
      </div>
    </div>
  `
})
export class MenuComponent implements AfterViewInit, OnChanges, OnDestroy {

  @Input() query = '';
  @Input() pageSize: number;
  @Input() menuElements: MenuElement[] = [];
  @Input() querySearchEnabled = true;

  @Output() onTabChange: EventEmitter<any> = new EventEmitter();
  @Output() onSearch: EventEmitter<any> = new EventEmitter();

  @ViewChild('menu') menu: ElementRef;
  @ViewChild('pageSizeInput') pageSizeInput: ElementRef;
  @ViewChild('queryInput') queryInput: ElementRef;

  private pageSize$: Subscription;
  private query$: Subscription;

  public menuElementRefs = [];

  constructor(private renderer: Renderer2) {
  }

  ngAfterViewInit(): void {
    this.menuElementRefs = this.menu.nativeElement.querySelectorAll('a.item');
    const emitChange = false;
    this.changeMenuElement(this.menuElements[0], emitChange); // first element is active by default

    if (this.pageSizeInput) {

      this.pageSizeInput.nativeElement.value = this.pageSize;

      this.pageSize$ = Observable
        .fromEvent(this.pageSizeInput.nativeElement, 'keyup')
        .map(e => (e as any).target.value)
        .debounceTime(700)
        .subscribe(this.onPageSizeInputChange.bind(this));
    }

    if (this.queryInput) {

      this.queryInput.nativeElement.value = this.query;

      this.query$ = Observable
        .fromEvent(this.queryInput.nativeElement, 'keyup')
        .map(e => (e as any).target.value)
        .debounceTime(700)
        .subscribe(this.onQueryInputChange.bind(this));
    }
  }


  ngOnChanges(changes: SimpleChanges): void {
    if (changes['pageSize'] && this.pageSizeInput) {
      this.pageSizeInput.nativeElement.value = changes['pageSize'].currentValue;
    }

    if (changes['query'] && this.queryInput) {
      this.queryInput.nativeElement.value = changes['query'].currentValue;
    }
  }

  ngOnDestroy(): void {
    if (this.pageSize$) {
      this.pageSize$.unsubscribe();
    }

    if (this.query$) {
      this.query$.unsubscribe();
    }
  }

  public changeMenuElement(menuElement: MenuElement, emitChange: boolean = true) {

    this.menuElementRefs.forEach(el => {
      if (el.innerText === menuElement.label) {
        this.renderer.addClass(el, 'active');
      } else {
        this.renderer.removeClass(el, 'active');
      }
    });

    if (emitChange) {
      this.onTabChange.emit(menuElement.value);
    }
  }

  public emitSearch(pageSize: number, query: string) {
    this.onSearch.emit({pageSize, query})
  }

  public trackByValue(index: number, menuElement: MenuElement) {
    return menuElement.value;
  }

  private onPageSizeInputChange = pageSizeValue => {
    if (pageSizeValue !== this.pageSize) {
      this.pageSize = pageSizeValue;
      this.emitSearch(this.pageSize, this.query)
    }
  }

  private onQueryInputChange = queryValue => {
    if (queryValue !== this.query) {
      this.query = queryValue;
      this.emitSearch(this.pageSize, this.query)
    }
  }
}
