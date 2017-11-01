import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {SharedModule} from '../../shared/shared.module';
import {AdminPanelComponent} from './admin-panel.component';
import {HttpModule} from '@angular/http';
import {AdminPanelServicesDetailsCardComponent} from './admin-panel-service-details-card.component';
import {NgSemanticModule} from 'ng-semantic';
import {AdminPanelMailCardComponent} from './admin-panel-mail-card.component';
import {FormsModule} from '@angular/forms';
import {AdminPanelUserCardComponent} from './admin-panel-user-card.component';

@NgModule({
  declarations: [
    AdminPanelComponent,
    AdminPanelMailCardComponent,
    AdminPanelServicesDetailsCardComponent,
    AdminPanelUserCardComponent
  ],
  imports: [
    CommonModule,
    FormsModule,
    HttpModule,
    NgSemanticModule,
    SharedModule
  ],
  exports: [
    AdminPanelComponent
  ]
})
export class AdminPanelModule {

}
