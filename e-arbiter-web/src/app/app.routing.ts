import {RouterModule, Routes} from "@angular/router";
import {NgModule} from "@angular/core";
import {MainComponent} from "./main.component";
import {DashboardComponent} from "./dashboard.component";
import {LogoutComponent} from "./logout.component";
import {AuthGuard} from "./auth.guard";
import {AnonymousUserGuard} from "app/anonymous-user.guard";

const appRoutes: Routes = [
  {path: '', redirectTo: '/main', pathMatch: 'full'},
  {path: 'logout', component: LogoutComponent, canActivate: [AnonymousUserGuard]},
  {path: 'dashboard', component: DashboardComponent, canActivate: [AuthGuard]},
  {path: 'main', component: MainComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(appRoutes)],
  exports:  [RouterModule]
})
export class AppRouting {
}
