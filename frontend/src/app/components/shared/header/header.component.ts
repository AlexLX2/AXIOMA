import { Component, Input } from '@angular/core';

@Component({
  selector: 'axioma-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss'],
})
export class HeaderComponent {
  @Input() title = '';
  @Input() subtitle = '';
  @Input() showSearchFilter = true;

  public isOpenedSortsDropdown = false;
  public isOpenedFilterDropdown = false;

  public toggleSortsDropdown = () =>
    (this.isOpenedSortsDropdown = !this.isOpenedSortsDropdown);
  public toggleFilterDropdown = () =>
    (this.isOpenedFilterDropdown = !this.isOpenedFilterDropdown);
}
