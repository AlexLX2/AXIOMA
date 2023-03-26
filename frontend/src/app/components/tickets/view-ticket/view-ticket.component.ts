import {Component, Input, OnChanges, OnInit, SimpleChanges} from '@angular/core';
import {Ticket} from "../../../interfaces/ticket";
import {TicketService} from "../../../services/ticket.service";

@Component({
    selector: 'app-view-ticket',
    templateUrl: './view-ticket.component.html',
    styleUrls: ['./view-ticket.component.scss']
})
export class ViewTicketComponent implements OnChanges, OnInit {
    currentTicket: Ticket | any = {}
    @Input() currentTicketId: number | undefined;

    constructor(private ticketService: TicketService) {


        // console.log('id', this.currentTicketId);
    }

    ngOnChanges(changes: SimpleChanges): void {
        console.log('changes', changes);
        this.currentTicketId = changes['currentTicketId'].currentValue;
        this.initData();
    }

    ngOnInit(): void {
       this.initData();
    }

    initData() {
        if (this.currentTicketId != null) {
            this.ticketService.getTicketById(this.currentTicketId).subscribe(data => {
                    this.currentTicket = data;
                }
            );
        }

        console.log('initiated', this.currentTicketId);
    }


}
