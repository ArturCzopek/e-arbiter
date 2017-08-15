import {NgModule} from "@angular/core";
import {MainPanelComponent} from "app/dashboard/main-panel/main-panel.component";
import {CommonModule} from "@angular/common";
import {SharedModule} from "../../shared/shared.module";

@NgModule({
  declarations: [
    MainPanelComponent
  ],
  imports: [
    CommonModule,
    SharedModule
  ],
  exports: [
    MainPanelComponent
  ]
})
export class MainPanelModule {

}
