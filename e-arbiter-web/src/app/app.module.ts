import {BrowserModule} from "@angular/platform-browser";
import {NgModule} from "@angular/core";
import {FormsModule} from "@angular/forms";
import {HttpModule} from "@angular/http";

import {AppComponent} from "./app.component";
import {AppRouting} from "./app.routing";
import {UserService} from "./shared/service/user.service";
import {LoggedInUserGuard} from "./shared/guard/logged-in-user.guard";
import {LoggedOutUserGuard} from "./shared/guard/logged-out-user.guard";
import {DashboardModule} from "./dashboard/dashboard.module";
import {HeaderComponent} from "./header.component";
import {LogoutComponent} from "./logout.component";
import {MainComponent} from "./main.component";

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
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
    UserService,
    LoggedInUserGuard,
    LoggedOutUserGuard
  ],
  bootstrap: [
    AppComponent
  ]
})
export class AppModule {
}
