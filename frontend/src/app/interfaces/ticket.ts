import {TicketBody} from "./ticket-body";

export interface Ticket {

    author: string;
    category: string;
    priority: string;
    status: string;
    ticketId: number;
    title: string;
    ticketBody?: TicketBody[];
    createdBy?: string;
    createdAt?: Date;
    changedBy?: string;
    changedAt?: Date;
}

