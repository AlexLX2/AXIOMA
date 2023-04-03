import {Component, OnInit} from '@angular/core';
import {TicketService} from "../../../services/ticket.service";
import {Ticket} from "../../../interfaces/ticket";
import {TitleService} from "../../../services/title.service";

@Component({
  selector: 'app-detail-ticket',
  templateUrl: './list-ticket.component.html',
  styleUrls: ['./list-ticket.component.scss']
})
export class ListTicketComponent implements OnInit {
  isTicketOpened: boolean = false;
  ticketList: Ticket[] = [];

  public currentTicketId: number = 0;
  constructor(private ticketService: TicketService,
              private titleService: TitleService) {
  }

  ngOnInit(): void {
    this.ticketService.getAllTickets().subscribe(data => {
      this.ticketList = data;
      console.log('Ticket list: ', this.ticketList);
    });
    this.titleService.showTitleMsg('Tickets', '', true);
  }

  openTicket(ticketId: number) {
    this.currentTicketId = ticketId;
    this.isTicketOpened = true;
  }
}
