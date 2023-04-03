import {Component, OnInit} from '@angular/core';
import {TicketService} from "../../services/ticket.service";
import {TitleService} from "../../services/title.service";

@Component({
  selector: 'app-reports',
  templateUrl: './reports.component.html',
  styleUrls: ['./reports.component.scss']
})
export class ReportsComponent implements OnInit{
  ticketList: any;
    constructor(private ticketService: TicketService,
                private titleService: TitleService) {
    }

  ngOnInit(): void {
      this.titleService.showTitleMsg('Reports', '', false);
      this.initData()
  }

  private initData() {
    this.ticketService.getAllTickets().subscribe(data => {
      this.ticketList = data;
    })
  }
}
