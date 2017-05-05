import {PreloadAllModules, RouterModule, Routes} from "@angular/router";
import {NgModule} from "@angular/core";
import {LogoutComponent} from "./logout.component";
import {LoggedOutUserGuard} from "./shared/guard/logged-out-user.guard";
import {MainComponent} from "./main.component";

const appRoutes: Routes = [
  {path: '', redirectTo: '/main', pathMatch: 'full'},
  {path: 'main', component: MainComponent},
  {path: 'dashboard', loadChildren: './dashboard/dashboard.module#DashboardModule'},
  {path: 'logout', component: LogoutComponent, canActivate: [LoggedOutUserGuard]}
];

@NgModule({
  imports: [RouterModule.forRoot(appRoutes, {preloadingStrategy: PreloadAllModules})],
  exports:  [RouterModule]
})
export class AppRouting {
}
