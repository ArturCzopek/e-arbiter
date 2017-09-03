import {Component} from "@angular/core";

@Component({
  selector: 'arb-main-panel',
  template: `
    <div class="ui container center aligned scrollable-page-view">
        <arb-main-panel-menu
          [currentTab]="currentTab" 
          [query]="query" 
          [tournamentsPerPage]="tournamentsPerPage" 
          (onTabChange)="changeTab($event)" 
          (onSearch)="findTournaments($event)"
        ></arb-main-panel-menu>
    </div>`
})
export class MainPanelComponent {

  public pageNr: number = 1;
  public tournamentsPerPage: number = 5;
  public query: string = "";
  public currentTab = "active";

  public changeTab(tabName: string) {
    console.log("tab", tabName);
  }

  public findTournaments(queryData: any) {
    console.log(queryData);
    this.tournamentsPerPage = queryData.tournamentsPerPage;
    this.query = queryData.query;
  }
}
