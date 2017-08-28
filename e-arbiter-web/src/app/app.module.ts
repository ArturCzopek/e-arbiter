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
import {LoginComponent} from "./login.component";
import {RouteService} from "./shared/service/route.service";
import {DevelopmentModeGuard} from "./shared/guard/development-mode.guard";
import {NotFoundComponent} from "./not-found.component";
import {HashLocationStrategy, LocationStrategy} from "@angular/common";

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    LogoutComponent,
    NotFoundComponent
  ],
  imports: [
    AppRouting,
    BrowserModule,
    DashboardModule,
    FormsModule,
    HttpModule
  ],
  providers: [
    AuthService,
    DevelopmentModeGuard,
    LoggedInUserGuard,
    LoggedOutUserGuard,
    RouteService,
    {provide: LocationStrategy, useClass: HashLocationStrategy}
  ],
  bootstrap: [
    AppComponent
  ]
})
export class AppModule {
}
