import {Component} from "@angular/core";
@Component({
  selector: 'arb-header',
  template: `
    <nav class="ui red inverted fixed menu">
      <div class="item"><a routerLink="/main" class="title">e-Arbiter</a></div>
      <div class="right menu">
        <div class="ui item">
          <a class="item">Otwarte turnieje</a>
          <a class="item">Moje turnieje</a>
          <a class="item">Ostatnie wyniki</a>
          <a class="item">O aplikacji</a>
        </div>
      </div>
    </nav>
  `,
  styleUrls: ['./app.scss']
})
export class HeaderComponent {

}
