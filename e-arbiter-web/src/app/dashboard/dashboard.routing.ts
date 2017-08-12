import {NgModule} from "@angular/core";
import {RouterModule, Routes} from "@angular/router";
import {DashboardComponent} from "./dashboard.component";
import {LoggedInUserGuard} from "../shared/guard/logged-in-user.guard";
import {DevelopmentCardComponent} from "./development-mode/development-card.component";
import {DevelopmentModeGuard} from "../shared/guard/development-mode.guard";
import {MainPanelComponent} from "./main-panel/main-panel.component";
import {AdminPanelComponent} from "app/dashboard/admin-panel/admin-panel.component";
import {ActiveTournamentsPanelComponent} from "./active-tournaments-panel/active-tournaments-panel.component";
import {TournamentManagementPanelComponent} from "./tournament-management-panel/tournament-management-panel.component";

const dashboardRoutes: Routes = [
  {
    path: '', component: DashboardComponent, canActivateChild: [LoggedInUserGuard],
    children: [
      {path: '', pathMatch: 'full', redirectTo: 'main'},
      {path: 'main', component: MainPanelComponent},
      {path: 'active', component: ActiveTournamentsPanelComponent},
      {path: 'management', component: TournamentManagementPanelComponent},
      {path: 'admin', component: AdminPanelComponent},
      {path: 'development', component: DevelopmentCardComponent, canActivate: [DevelopmentModeGuard]}
    ]
  },
];

@NgModule({
  imports: [RouterModule.forChild(dashboardRoutes)],
  exports: [RouterModule]
})
export class DashboardRouting {

}
