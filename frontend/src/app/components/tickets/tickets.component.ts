import { Component } from '@angular/core';

@Component({
  selector: 'axioma-tickets',
  templateUrl: './tickets.component.html',
  styleUrls: ['./tickets.component.scss']
})
export class TicketsComponent {

  public isOpenedSortsDropdown = false;
  public isOpenedFilterDropdown = false;

  public toggleSortsDropdown = () => this.isOpenedSortsDropdown = !this.isOpenedSortsDropdown;
  public toggleFilterDropdown = () => this.isOpenedFilterDropdown = !this.isOpenedFilterDropdown;


}
