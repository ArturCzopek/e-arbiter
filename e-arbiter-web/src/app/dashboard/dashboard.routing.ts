import {RouterModule, Routes} from "@angular/router";
import {UserDataComponent} from "./user-data.component";
import {NgModule} from "@angular/core";
import {DashboardComponent} from "./dashboard.component";

export const dashboardRoutes: Routes = [
  {
    path: '',
    component: DashboardComponent,
    children: [
      {path: '', pathMatch: 'full', redirectTo: 'data'},
      {path: 'data', component: UserDataComponent}
    ]
  }
];

@NgModule({
  imports: [
    RouterModule.forChild(dashboardRoutes)
  ],
  exports: [
    RouterModule
  ]
})
export class DashboardRouting {
}
