import {Component, OnInit} from '@angular/core';
import {CatalogService} from "../../../services/catalog.service";
import {Catalog} from "../../../interfaces/catalog";
import {TicketService} from "../../../services/ticket.service";
import {TicketDto} from "../../../dto/ticket-dto";
import {Employee} from "../../../interfaces/employee";
import {EmployeeService} from "../../../services/employee.service";
import {FormBuilder, FormControl, FormGroup} from "@angular/forms";

@Component({
    selector: 'app-new-ticket',
    templateUrl: './new-ticket.component.html',
    styleUrls: ['./new-ticket.component.scss']
})
export class NewTicketComponent implements OnInit {

    priorityList: Catalog[] = [];

    employeeList: Employee[] = [];

    categoryList: Catalog[] = [];

    statusList: Catalog[] = [];

    newTicket?: TicketDto;
    fileName: string = 'Select file';
    vForm: FormGroup;
    currentFile?: File;
    files: File[] = [];

    constructor(private catalogService: CatalogService,
                private ticketService: TicketService,
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

        console.log('client', this.vForm.get('client')?.value);
        console.log('empl', this.vForm.get('employee')?.value);
        console.log('body', this.vForm.get('body')?.value);


        this.newTicket = new TicketDto(0,
            this.vForm.get('subject')?.value,
            "AK",
            this.vForm.get('category')?.value,
            this.vForm.get('priority')?.value,
            this.vForm.get('status')?.value,
            this.vForm.get('body')?.value,
            [],
            this.vForm.get('client')?.value,
            this.vForm.get('employee')?.value);

        console.log('new ticket', this.newTicket);

        this.ticketService.createTicket(this.newTicket).subscribe(data => {
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
