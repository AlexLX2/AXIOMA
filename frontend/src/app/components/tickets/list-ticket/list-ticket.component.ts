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

  pageSizes: number[] = [10,20,50];

  pageSize: number = 10;
  pagNum: number = 1;
  totalTickets: number = 1;

  constructor(private ticketService: TicketService,
              private titleService: TitleService,
              private footerService: FooterService,
              private loaderService: LoaderService) {
  }

  ngOnInit(): void {

    this.titleService.showTitleMsg('Tickets', '', true);
    this.footerService.enablePagination(false);
    this.loaderService.show();
    this.ticketService.getTicketCount().subscribe(count => {
      this.totalTickets = count;
    })
    this.initTicketList();

  }

  openTicket(ticketId: number) {
    this.currentTicketId = ticketId;
    this.isTicketOpened = true;
  }

   initTicketList() {
    console.log('curent page', this.pagNum);
    this.ticketService.getAllTickets(this.pageSize, this.pagNum).subscribe(data => {
      this.ticketList = data;
      console.log('Ticket list: ', this.ticketList);
      this.loaderService.hide();
    });
  }

  changePageNum($event: number) {
    this.pagNum = $event;
    this.initTicketList();
  }

  changePageSize($event: number) {
    this.pageSize = $event;
    this.initTicketList();
  }
}
