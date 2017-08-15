import {Component} from '@angular/core';

@Component({
  selector: 'arb-root',
  template: `
    <div>
      <router-outlet></router-outlet>
    </div>
  `
})
export class AppComponent {
}
