import {TicketAttachment} from "./ticket-attachment";

export interface TicketBody {
    body: string;

    to: string;

    from: string;

    id: number;

    subject: string;

    ticketAttachment: TicketAttachment[];
}
