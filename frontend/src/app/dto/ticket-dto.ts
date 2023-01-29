import {Catalog} from "../interfaces/catalog";
import {TicketAttachment} from "../interfaces/ticket-attachment";

export class TicketDto {

    constructor(private id: number,
                private subject: string,
                private author: string,
                private category: Catalog,
                private priority: Catalog,
                private status: Catalog,
                private body: string,
                private attachments: TicketAttachment[],
                private client: string,
                private to: string
                ) {
    }

}
