import {TicketBody} from "./ticket-body";

export interface Ticket {

    author: string;
    category: string;
    changedAt: Date;
    changedBy: string;
    createdAt: Date;
    createdBy: string;
    priority: string;
    status: string;
    ticketId: number;
    title: string;
    ticketBody?: TicketBody[];
}

