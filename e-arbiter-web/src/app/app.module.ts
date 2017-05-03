import {BrowserModule} from "@angular/platform-browser";
import {NgModule} from "@angular/core";
import {FormsModule} from "@angular/forms";
import {HttpModule} from "@angular/http";

import {AppComponent} from "./app.component";
import {AppRouting} from "./app.routing";
import {MainComponent} from "./main.component";
import {UserService} from "./user.service";
import {DashboardComponent} from "./dashboard.component";
import {LogoutComponent} from "./logout.component";
import {AuthGuard} from "./auth.guard";
import {AnonymousUserGuard} from "./anonymous-user.guard";

@NgModule({
  declarations: [
    AppComponent,
    MainComponent,
    DashboardComponent,
    LogoutComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpModule,
    AppRouting
  ],
  providers: [
    UserService,
    AuthGuard,
    AnonymousUserGuard
  ],
  bootstrap: [
    AppComponent
  ]
})
export class AppModule {
}
