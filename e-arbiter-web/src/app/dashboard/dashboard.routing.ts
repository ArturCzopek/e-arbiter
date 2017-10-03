import {NgModule} from "@angular/core";
import {RouterModule, Routes} from "@angular/router";
import {DashboardComponent} from "./dashboard.component";
import {LoggedInUserGuard} from "../shared/guard/logged-in-user.guard";
import {DevelopmentCardComponent} from "./development-mode/development-card.component";
import {DevelopmentModeGuard} from "../shared/guard/development-mode.guard";
import {MainPanelComponent} from "./main-panel/main-panel.component";
import {AdminPanelComponent} from "app/dashboard/admin-panel/admin-panel.component";
import {TournamentManagementPanelComponent} from "./tournament-management-panel/tournament-management-panel.component";
import {TournamentDetailsComponent} from "app/dashboard/main-panel/tournament-details.component";
import {TournamentFormComponent} from "app/dashboard/tournament-management-panel/tournament-form.component";
import {ActiveTournamentsComponent} from "./active-tournaments-panel/active-tournaments.component";

const dashboardRoutes: Routes = [
  {
    path: '', component: DashboardComponent, canActivateChild: [LoggedInUserGuard],
    children: [
      {path: '', pathMatch: 'full', redirectTo: 'main'},
      {path: 'main', component: MainPanelComponent},
      {path: 'tournament/:id', component: TournamentDetailsComponent},
      {path: 'active', component: ActiveTournamentsComponent},
      {path: 'management', component: TournamentManagementPanelComponent},
      {path: 'management/new', component: TournamentFormComponent},
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
