export interface TicketAttachment {
    bodyId?: number,
    id?:number,
    file?: File,
    fileName: string,
    fileContent?: string | ArrayBuffer,
    fileType: string
}
