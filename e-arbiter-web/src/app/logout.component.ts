import {Component, OnInit} from "@angular/core";
import {Router} from "@angular/router";

@Component({
  selector: 'arb-logout',
  template: `
    <div class="ui text container">
      <h1 class="ui inverted header">e-Arbiter</h1>
      <h2>Zostałeś wylogowany</h2>
    </div>`,
  styleUrls: ['./app.scss']
})
export class LogoutComponent implements OnInit {

  constructor(private router: Router) {

  }

  ngOnInit() {
    setTimeout(() => this.router.navigate(['/main']), 3000);
  }
}
