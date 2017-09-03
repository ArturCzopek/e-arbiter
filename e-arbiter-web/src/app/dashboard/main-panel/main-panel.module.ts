import {NgModule} from "@angular/core";
import {MainPanelComponent} from "app/dashboard/main-panel/main-panel.component";
import {CommonModule} from "@angular/common";
import {SharedModule} from "../../shared/shared.module";
import {MainPanelMenuComponent} from "./main-panel-menu.component";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";

@NgModule({
  declarations: [
    MainPanelComponent,
    MainPanelMenuComponent
  ],
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    SharedModule
  ],
  exports: [
    MainPanelComponent
  ]
})
export class MainPanelModule {

}
