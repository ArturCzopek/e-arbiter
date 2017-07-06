import {BrowserModule} from "@angular/platform-browser";
import {NgModule} from "@angular/core";
import {FormsModule} from "@angular/forms";
import {HttpModule} from "@angular/http";

import {AppComponent} from "./app.component";
import {AppRouting} from "./app.routing";
import {AuthService} from "./shared/service/auth.service";
import {LoggedInUserGuard} from "./shared/guard/logged-in-user.guard";
import {LoggedOutUserGuard} from "./shared/guard/logged-out-user.guard";
import {DashboardModule} from "./dashboard/dashboard.module";
import {LogoutComponent} from "./logout.component";
import {MainComponent} from "./main.component";

@NgModule({
  declarations: [
    AppComponent,
    LogoutComponent,
    MainComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpModule,
    DashboardModule,
    AppRouting
  ],
  providers: [
    AuthService,
    LoggedInUserGuard,
    LoggedOutUserGuard
  ],
  bootstrap: [
    AppComponent
  ]
})
export class AppModule {
}
