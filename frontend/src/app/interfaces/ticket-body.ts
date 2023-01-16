import {TicketAttachment} from "./ticket-attachment";

export interface TicketBody {
    body: string;

    changedBy: string;

    createdAt: Date;

    createdBy: string;

    from: string;

    id: number;

    subject: string;

    ticketAttachment: TicketAttachment[];

    to: string;
}
