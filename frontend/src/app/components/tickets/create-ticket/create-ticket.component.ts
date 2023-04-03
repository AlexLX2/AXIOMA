import {Component, OnInit} from '@angular/core';
import {CatalogService} from "../../../services/catalog.service";
import {Catalog} from "../../../interfaces/catalog";
import {TicketService} from "../../../services/ticket.service";
import {Employee} from "../../../interfaces/employee";
import {EmployeeService} from "../../../services/employee.service";
import {FormBuilder, FormControl, FormGroup} from "@angular/forms";
import {Ticket} from "../../../interfaces/ticket";
import {TicketBody} from "../../../interfaces/ticket-body";
import {TitleService} from "../../../services/title.service";

@Component({
    selector: 'app-new-ticket',
    templateUrl: './create-ticket.component.html',
    styleUrls: ['./create-ticket.component.scss']
})
export class CreateTicketComponent implements OnInit {

    priorityList: Catalog[] = [];

    employeeList: Employee[] = [];

    categoryList: Catalog[] = [];

    statusList: Catalog[] = [];

    newTicket?: Ticket;
    fileName: string = 'Select file';
    vForm: FormGroup;
    currentFile?: File;
    files: File[] = [];

    constructor(private catalogService: CatalogService,
                private ticketService: TicketService,
                private titleService: TitleService,
                private emplService: EmployeeService,
                private fb: FormBuilder) {

        this.vForm = fb.group(
            {
                employee:     new FormControl(''),
                status:       new FormControl(null),
                client:       new FormControl(''),
                subject:      new FormControl(''),
                category:     new FormControl(null),
                priority:     new FormControl(null),
                file:         new FormControl(''),
                body:         new FormControl('')
            }
        );


    }

    ngOnInit(): void {
        this.titleService.showTitleMsg('Create ticket', '', false);
        this.emplService.getAllEmployees().subscribe(data => {
            this.employeeList = data;
        });

        this.catalogService.getCatalogItemList('status').subscribe(
            data => {
                this.statusList = data;
            }
        );

        this.catalogService.getCatalogItemList('priority').subscribe(
            data => {
                this.priorityList = data;
            }
        )

        this.catalogService.getCatalogItemList('category').subscribe(
            data => {
                this.categoryList = data;
            }
        )


    }

    createTicket() {

        const ticketBody: TicketBody[] = [];
        ticketBody.push(
            {
                body: this.vForm.get('body')?.value,
                from: "AK",
                id: 0,
                subject: this.vForm.get('subject')?.value,
                ticketAttachment: [],
                to: "VB"
            }
        )

        this.newTicket = {
            author: "AK",
            category:  this.vForm.get('category')?.value,
            priority: this.vForm.get('priority')?.value,
            status:   this.vForm.get('status')?.value,
            ticketBody: ticketBody,
            ticketId: 0,
            title:  this.vForm.get('subject')?.value};

        console.log('new ticket', this.newTicket);

        this.ticketService.createTicketHeader(this.newTicket).subscribe(data => {
            console.log('createticket', data);
        });
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

}
