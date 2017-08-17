import {PreloadAllModules, RouterModule, Routes} from "@angular/router";
import {NgModule} from "@angular/core";
import {LogoutComponent} from "./logout.component";
import {LoggedOutUserGuard} from "./shared/guard/logged-out-user.guard";
import {LoginComponent} from "./login.component";
import {NotFoundComponent} from "./not-found.component";

const appRoutes: Routes = [
  {path: '', redirectTo: 'login', pathMatch: 'full'},
  {path: 'login', component: LoginComponent},
  {path: 'login/:oauth-token', component: LoginComponent},
  {path: 'dashboard', loadChildren: './dashboard/dashboard.module#DashboardModule'},
  {path: 'logout', component: LogoutComponent, canActivate: [LoggedOutUserGuard]},
  {path: '**', component: NotFoundComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(appRoutes, {preloadingStrategy: PreloadAllModules})],
  exports:  [RouterModule]
})
export class AppRouting {
}
