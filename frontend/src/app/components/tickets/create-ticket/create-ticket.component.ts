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
import {UserService} from "../../../services/user.service";
import {Role} from "../../../interfaces/role";
import {AlertService} from "../../../_alert";
import {Router} from "@angular/router";
import {FooterService} from "../../../services/footer.service";
import {TicketAttachment} from "../../../interfaces/ticket-attachment";

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
    roleList: Role[] = [];
    attachmentList: TicketAttachment[] = [];

    newTicket?: Ticket;
    fileName: string = 'Select file';
    vForm: FormGroup;
    currentFile?: File;
    files: File[] = [];

    constructor(private catalogService: CatalogService,
                private ticketService: TicketService,
                private titleService: TitleService,
                private footerService: FooterService,
                private alertService: AlertService,
                private emplService: EmployeeService,
                private userService: UserService,
                private router: Router,
                private fb: FormBuilder) {

        this.vForm = fb.group(
            {
                role: new FormControl(''),
                status: new FormControl(null),
                client: new FormControl(''),
                subject: new FormControl(''),
                category: new FormControl(null),
                priority: new FormControl(null),
                file: new FormControl(''),
                body: new FormControl('')
            }
        );
    }

    ngOnInit(): void {
        this.titleService.showTitleMsg('Create ticket', '', false);
        this.footerService.enablePagination(false);

        this.emplService.getAllEmployees().subscribe(data => {
            this.employeeList = data;
        });

        this.userService.getAllRoles().subscribe(roles => {
            this.roleList = roles;
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
        );

        this.catalogService.getCatalogItemList('category').subscribe(
            data => {
                this.categoryList = data;
            }
        );
    }

    createTicket() {

        const ticketBody: TicketBody[] = [];
        ticketBody.push(
            {
                body: this.vForm.get('body')?.value
            }
        )

        if(this.attachmentList.length>0) {
            console.log('atta', this.attachmentList);
            ticketBody[0].ticketAttachment = this.attachmentList;
        }

        this.newTicket = {
            author: 'AK (ak@akdev.md)',
            category: this.vForm.get('category')?.value,
            priority: this.vForm.get('priority')?.value,
            status: this.vForm.get('status')?.value,
            ticketBody: ticketBody,
            ticketId: 0,
            title: this.vForm.get('subject')?.value,
            acl: {id: this.vForm.get('role')?.value}
        };

        console.log('new ticket', this.newTicket);

        this.ticketService.createTicket(this.newTicket).subscribe(data => {
            this.alertService.success(data.result, {
                keepAfterRouteChange: true,
                autoClose: true
            });
            this.router.navigateByUrl('/tickets');
        }, error => {
            this.alertService.error(error.result);
        });
    }

    selectFile(event: any): void {
        if (event.target.files && event.target.files[0]) {
            [...event.target.files].forEach((file: File) => {
                this.files.push(file)
                this.fileName = this.fileName + file.name;
                const reader = new FileReader();
                reader.readAsDataURL(file);
                reader.onload = () => {
                    this.attachmentList.push({
                        fileContent: reader.result? reader.result: '',
                        fileName: file.name,
                        fileType: file.type
                    });
                };
            })
        } else {
            this.fileName = 'Select File';
        }
    }

}
