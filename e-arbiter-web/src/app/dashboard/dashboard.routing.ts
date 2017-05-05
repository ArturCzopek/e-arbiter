import {NgModule} from "@angular/core";
import {RouterModule, Routes} from "@angular/router";
import {DashboardComponent} from "./dashboard.component";
import {LoggedInUserGuard} from "../shared/guard/logged-in-user.guard";
import {UserDataComponent} from "./user-data.component";

const dashboardRoutes: Routes = [
  {path: '', component: DashboardComponent,
    children: [
      {path: '', pathMatch: 'full', redirectTo: 'data'},
      {path: 'data', component: UserDataComponent, canActivate: [LoggedInUserGuard]}
    ]},
];

@NgModule({
  imports: [RouterModule.forChild(dashboardRoutes)],
  exports: [RouterModule]
})
export class DashboardRouting {

}
