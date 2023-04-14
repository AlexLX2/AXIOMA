import {TicketAttachment} from "./ticket-attachment";

export interface TicketBody {
    body: string;

    id: number;

    ticketAttachment: TicketAttachment[];
}
