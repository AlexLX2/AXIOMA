import {TicketBody} from "./ticket-body";
import {Catalog} from "./catalog";

export interface Ticket {

    author: string;
    category: Catalog;
    priority: Catalog;
    status: Catalog;
    ticketId: number;
    title: string;
    ticketBody?: TicketBody[];
    createdBy?: string;
    createdAt?: Date;
    changedBy?: string;
    changedAt?: Date;
    acl?: {id:number};
}

