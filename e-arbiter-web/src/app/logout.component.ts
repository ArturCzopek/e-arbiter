import {Component, OnInit} from "@angular/core";
import {Router} from "@angular/router";

@Component({
  selector: 'arb-logout',
  template: `<p>You've been logout</p>`,
  styleUrls: ['./app.component.css']
})
export class LogoutComponent implements OnInit {

  constructor(private router: Router) {

  }

  ngOnInit() {
    setTimeout(() => this.router.navigate(['/']), 3000);
  }
}
