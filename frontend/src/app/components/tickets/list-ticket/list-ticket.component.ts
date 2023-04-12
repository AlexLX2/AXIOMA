import {Component, OnInit} from '@angular/core';
import {TicketService} from "../../../services/ticket.service";
import {Ticket} from "../../../interfaces/ticket";
import {TitleService} from "../../../services/title.service";
import {FooterService} from "../../../services/footer.service";
import {LoaderService} from "../../../services/loader.service";

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
              private titleService: TitleService,
              private footerService: FooterService,
              private loaderService: LoaderService) {
  }

  ngOnInit(): void {
    this.titleService.showTitleMsg('Tickets', '', true);
    this.footerService.enablePagination(false);
    this.loaderService.show();
    this.ticketService.getAllTickets().subscribe(data => {
      this.ticketList = data;
      console.log('Ticket list: ', this.ticketList);
         this.loaderService.hide();
    });

  }

  openTicket(ticketId: number) {
    this.currentTicketId = ticketId;
    this.isTicketOpened = true;
  }
}
