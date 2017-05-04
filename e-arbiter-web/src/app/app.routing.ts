import {RouterModule, Routes} from "@angular/router";
import {NgModule} from "@angular/core";
import {LogoutComponent} from "./logout.component";
import {LoggedOutUserGuard} from "./shared/guard/logged-out-user.guard";
import {MainComponent} from "./main.component";
import {LoggedInUserGuard} from "./shared/guard/logged-in-user.guard";

const appRoutes: Routes = [
  {path: '', redirectTo: '/main', pathMatch: 'full'},
  {path: 'main', component: MainComponent, canActivate: [LoggedOutUserGuard]},
  {path: 'dashboard', loadChildren: 'app/dashboard/dashboard.module#DashboardModule', canActivate: [LoggedInUserGuard], outlet: "db"},
  {path: 'logout', component: LogoutComponent, canActivate: [LoggedOutUserGuard]}
];

@NgModule({
  imports: [RouterModule.forRoot(appRoutes)],
  exports:  [RouterModule]
})
export class AppRouting {
}
