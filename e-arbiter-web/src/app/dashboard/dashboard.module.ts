import {NgModule} from "@angular/core";
import {FormsModule} from "@angular/forms";
import {HttpModule} from "@angular/http";

import {CommonModule} from "@angular/common";
import {DashboardComponent} from "./dashboard.component";
import {UserDataComponent} from "./user-data.component";
import {DashboardRouting} from "./dashboard.routing";

@NgModule({
  declarations: [
    DashboardComponent,
    UserDataComponent
  ],
  imports: [
    CommonModule,
    FormsModule,
    HttpModule,
    DashboardRouting
  ],
  exports: [
    DashboardComponent,
  ],
  providers: [
  ]
})
export class DashboardModule {
}
