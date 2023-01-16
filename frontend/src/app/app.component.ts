import { Component } from '@angular/core';
import { SidebarService } from './components/shared/sidebar/sidebar.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  title = 'axioma-frontend';
  public theme = "dark";

  constructor(public sidebarService: SidebarService) {

  }
}
