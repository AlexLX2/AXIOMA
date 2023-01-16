import {Component, OnInit} from '@angular/core';
import {TicketService} from "../../../services/ticket.service";
import {Ticket} from "../../../interfaces/ticket";
import {ActivatedRoute} from "@angular/router";
import {TicketBody} from "../../../interfaces/ticket-body";

@Component({
  selector: 'app-detail-ticket',
  templateUrl: './detail-ticket.component.html',
  styleUrls: ['./detail-ticket.component.scss']
})
export class DetailTicketComponent implements OnInit{
  isTicketOpened: boolean = false;

  ticketList: Ticket[] = [];

  currentTicket: Ticket;

  ticketMainBody: TicketBody = {
    id: 0,
    body: '',
    createdAt: new Date(),
    createdBy: '',
    changedBy: '',
    subject: '',
    from: '',
    to: '',
    ticketAttachment: []
  }
  private currentTicketId: number = 0;
  constructor(private ticketService: TicketService,
              private route: ActivatedRoute) {
      this.currentTicket = {
        author: "",
        category: "",
        changedAt: new Date(),
        changedBy: "",
        createdAt: new Date(),
        createdBy: "",
        priority: "",
        status: "",
        ticketId : 0,
        title: '',
        ticketBody: []
      }
  }

  ngOnInit(): void {
    this.ticketService.getAllTickets().subscribe(data => {
      this.ticketList = data;
      console.log('Ticket list: ', this.ticketList);
    })

    this.currentTicketId = this.route.snapshot.params['id'] || 0;

    console.log('current id', this.currentTicketId)

    if((this.currentTicketId) !==0) {
      this.ticketService.getTicketById(this.currentTicketId).subscribe(data => {
        this.currentTicket = data;

        if (this.currentTicket.ticketBody) {
           this.ticketMainBody = this.currentTicket.ticketBody[0];
        }

        console.log('Current ticket: ', data)
      })
      this.isTicketOpened = true;
    }
  }

}
