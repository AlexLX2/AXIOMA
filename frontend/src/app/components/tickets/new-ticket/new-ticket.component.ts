import { Component } from '@angular/core';
import {CatalogService} from "../../../services/catalog.service";
import {Catalog} from "../../../interfaces/catalog";
import {Ticket} from "../../../interfaces/ticket";
import {TicketService} from "../../../services/ticket.service";
import {TicketBody} from "../../../interfaces/ticket-body";
import {TicketDto} from "../../../dto/ticket-dto";
import {AngularEditorConfig} from "@kolkov/angular-editor";
import {Editor} from "ngx-editor";

@Component({
  selector: 'app-new-ticket',
  templateUrl: './new-ticket.component.html',
  styleUrls: ['./new-ticket.component.scss']
})
export class NewTicketComponent {


  categoryName: string = 'category';
  selectedCategory: Catalog = { id:1, name:'Support'};
  priorityList: Catalog[] = [];
  priorityName: string = 'priority';

  editorConfig: AngularEditorConfig = {
      editable: true,
      height: '100%',
      minHeight: '150px',
      maxHeight: 'auto',
      placeholder: 'Tell us more about your problem...'
}

    editor: Editor = new Editor();
  selectedPriority: Catalog = {id:1, name: 'Normal'};
  statusList: Catalog[] = [];

  selectedStatus: Catalog = {id:1, name: 'New'};

  newTicket?: TicketDto;
  isCategoryOpened: boolean = false;
  isPriorityOpened: boolean = false;
  body: string = '';

    constructor(private catalogService: CatalogService,
                private ticketService: TicketService) {

    this.catalogService.getCatalogItemList('status').subscribe(
        data => {
          this.statusList = data;
        }
    )
  }

  openCategories() {
      this.isCategoryOpened = !this.isCategoryOpened;
      this.isPriorityOpened = false;
  }

  openPriorities() {
    this.isPriorityOpened = !this.isPriorityOpened;
    this.isCategoryOpened = false;
  }
  handleSelect($event: any) {
      console.log('event', $event)
      switch ($event.type) {
          case 'priority':{
              this.selectedPriority = $event.value;
              break;
          }
          case 'category': {
              this.selectedCategory = $event.value;
              break;
          }
          default:
              console.log('WTF?', $event.type)
              break;
      }
  }

  createTicket() {

        console.log('body', this.body);

        this.newTicket = new TicketDto(0,
            "Test subj",
            "AK",
            this.selectedCategory,
            this.selectedPriority,
            this.selectedStatus,
            this.body,
            [],
            "Test client",
            "Vi Bri");

        console.log('new ticket', this.newTicket);

        this.ticketService.createTicket(this.newTicket).subscribe(data => {
            console.log('createticket', data);
        });
  }

}
