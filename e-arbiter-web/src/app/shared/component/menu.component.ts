import {AfterViewInit, Component, ElementRef, EventEmitter, Input, Output, Renderer2, ViewChild} from '@angular/core';
import {MenuElement} from '../model/menu-element.model';

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
          <form (keyup.enter)="emitSearch(pageSize, query)" class="ui form no-margin-below" [ngClass]="querySearchEnabled ? '' : 'corner-tiny-form'">
            <div [ngClass]="querySearchEnabled ? 'fields' : 'one field'">
              <div class="field narrow">
                <input
                  type="number"
                  min="0"
                  step="1"
                  placeholder="ile?"
                  [(ngModel)]="pageSize"
                  [ngModelOptions]="{standalone: true}">
              </div>
              <div *ngIf="querySearchEnabled" class="field broad ui icon input">
                <input
                  type="text"
                  placeholder="Szukaj turniejów..."
                  [(ngModel)]="query"
                  [ngModelOptions]="{standalone: true}">
                <i class="search link icon"></i>
              </div>
            </div>
          </form>
        </div>
      </div>
    </div>
  `
})
export class MenuComponent implements AfterViewInit {

  @Input() query = '';
  @Input() pageSize: number;
  @Input() menuElements: MenuElement[] = [];
  @Input() querySearchEnabled = true;

  @Output() onTabChange: EventEmitter<any> = new EventEmitter();
  @Output() onSearch: EventEmitter<any> = new EventEmitter();

  @ViewChild('menu') menu: ElementRef;

  public menuElementRefs = [];

  constructor(private renderer: Renderer2) {
  }

  ngAfterViewInit(): void {
    this.menuElementRefs = this.menu.nativeElement.querySelectorAll('a.item');
    const emitChange = false;
    this.changeMenuElement(this.menuElements[0], emitChange); // first element is active by default
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
}
