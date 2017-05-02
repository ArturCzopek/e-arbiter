import {RouterModule, Routes} from "@angular/router";
import {NgModule} from "@angular/core";
import {LoginComponent} from "./login.component";

const appRoutes: Routes = [
  {path: '', redirectTo: '/login/', pathMatch: 'full'},
  {path: 'login/:oauth_token', component: LoginComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(appRoutes)],
  exports:  [RouterModule]
})
export class AppRouting {
}
