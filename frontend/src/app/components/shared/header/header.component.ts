import {Component, Input, OnInit} from '@angular/core';
import {TitleService} from "../../../services/title.service";

@Component({
  selector: 'axioma-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss'],
})
export class HeaderComponent implements OnInit{
   title = '';
   subtitle = '';
   showSearchFilter = true;

  public isOpenedSortsDropdown = false;
  public isOpenedFilterDropdown = false;

  constructor(private titleService: TitleService) {
  }

  public toggleSortsDropdown = () =>
    (this.isOpenedSortsDropdown = !this.isOpenedSortsDropdown);
  public toggleFilterDropdown = () =>
    (this.isOpenedFilterDropdown = !this.isOpenedFilterDropdown);

  ngOnInit(): void {
    this.titleService.title.asObservable().subscribe(value => {
      this.title = value;
    });
    this.titleService.subTitle.asObservable().subscribe(value => {
      this.subtitle = value;
    })
    this.titleService.showSearch.asObservable().subscribe(value => {
      this.showSearchFilter = value;
    })
  }
}
