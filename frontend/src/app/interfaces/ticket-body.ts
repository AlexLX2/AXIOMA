import {TicketAttachment} from "./ticket-attachment";

export interface TicketBody {
    body: string;
    ticket: number;
    id: number;
    ticketAttachment?: TicketAttachment[];
}
