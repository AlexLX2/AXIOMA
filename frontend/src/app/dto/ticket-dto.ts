import {Catalog} from "../interfaces/catalog";
import {TicketAttachment} from "../interfaces/ticket-attachment";

export class TicketDto {

    constructor(private ticketId: number,
                private title: string,
                private author: string,
                private category: number,
                private priority: number,
                private status: number,
                private body: string,
                private attachments: TicketAttachment[],
                private client: string,
                private to: string[]
                ) {
    }

}
