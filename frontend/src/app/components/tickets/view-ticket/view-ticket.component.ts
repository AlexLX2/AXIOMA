import {Component, Input, OnChanges, OnInit, SimpleChanges} from '@angular/core';
import {Ticket} from "../../../interfaces/ticket";
import {TicketService} from "../../../services/ticket.service";
import {TicketBody} from "../../../interfaces/ticket-body";
import {Router} from "@angular/router";
import {AlertService} from "../../../_alert";
import {FormGroup} from "@angular/forms";
import FileSaver from "file-saver";

@Component({
    selector: 'app-view-ticket',
    templateUrl: './view-ticket.component.html',
    styleUrls: ['./view-ticket.component.scss']
})
export class ViewTicketComponent implements OnChanges, OnInit {
    currentTicket: Ticket | any = {}
    @Input() currentTicketId: number | undefined;
    body: string = '';

    fileName: string = 'Select file';
    currentFile?: File;
    files: File[] = [];
    constructor(private ticketService: TicketService,
                private router: Router,
                private alertService: AlertService) {}

    ngOnChanges(changes: SimpleChanges): void {
        console.log('changes', changes);
        this.currentTicketId = changes['currentTicketId'].currentValue;
        this.initData();
    }

    ngOnInit(): void {
       this.initData();
    }

    initData() {
        if (this.currentTicketId != null) {
            this.ticketService.getTicketById(this.currentTicketId).subscribe(data => {
                    this.currentTicket = data;

                }
            );
        }
    }

    isAgentBody(ticketBody: any): boolean {
        return ticketBody.id % 3=== 0;
    }

    selectFile(event: any): void {
        console.log('Event', event);
        if (event.target.files && event.target.files[0]) {
            [...event.target.files].forEach((file: File) => {
                this.files.push(file)
                this.fileName = this.fileName + file.name;
            })
        } else {
            this.fileName = 'Select File';
        }
    }

    createBody() {
        const ticketBody: TicketBody = <TicketBody>{
            body: this.body,
            id: 0,
            ticket: this.currentTicketId
        };

        this.ticketService.createTicketBody(ticketBody).subscribe(data => {
            if(this.files.length>0) {
                this.ticketService.createTicketAttachment(data.result, this.files).subscribe(files => {
                });
            }

            this.alertService.success(data.result, {
                keepAfterRouteChange: true,
                autoClose: true
            });
            this.router.navigateByUrl('tickets/');
            }
        );
    }

    openFile($event: any) {
        this.ticketService.getAttachmentById($event).subscribe(data => {
            console.log('data', data);
            let fileName = data.headers.get('Content-Disposition')
            fileName = fileName.split('=')[1];
            fileName = fileName.substring(1, fileName.length-1);
            const blob = new Blob([data.body]);
            const url = window['URL'].createObjectURL(blob);
            const link = document.createElement('a');
            link.href = url;
            link.download = fileName;
            link.click();
            console.log('filenane', fileName);
            console.log('blol', blob);
        })
    }
}
