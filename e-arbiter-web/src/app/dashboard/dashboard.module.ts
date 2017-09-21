import {NgModule} from "@angular/core";
import {FormsModule} from "@angular/forms";
import {HttpModule} from "@angular/http";

import {CommonModule} from "@angular/common";
import {DashboardComponent} from "./dashboard.component";
import {DevelopmentCardComponent} from "./development-mode/development-card.component";
import {HeaderComponent} from "./header.component";
import {DashboardRouting} from "./dashboard.routing";
import {MainPanelModule} from "./main-panel/main-panel.module";
import {AdminPanelModule} from "./admin-panel/admin-panel.module";
import {ActiveTournamentsModule} from "./active-tournaments-panel/active-tournaments.module";
import {TournamentManagementPanelModule} from "./tournament-management-panel/tournament-management-panel.module";

@NgModule({
  declarations: [
    DashboardComponent,
    DevelopmentCardComponent,
    HeaderComponent
  ],
  imports: [
    ActiveTournamentsModule,
    AdminPanelModule,
    CommonModule,
    DashboardRouting,
    FormsModule,
    HttpModule,
    MainPanelModule,
    TournamentManagementPanelModule
  ],
  exports: [
    DashboardComponent
  ],
  providers: [
  ]
})
export class DashboardModule {
}
