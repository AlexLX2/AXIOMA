import { Component } from '@angular/core';

@Component({
  selector: 'app-detail-ticket',
  templateUrl: './detail-ticket.component.html',
  styleUrls: ['./detail-ticket.component.scss']
})
export class DetailTicketComponent {
  isTicketOpened: boolean = false;

  toggle() {
    this.isTicketOpened = !this.isTicketOpened;
  }
}
