import {Component, OnInit} from '@angular/core';
import {TitleService} from "../../../services/title.service";
import {FooterService} from "../../../services/footer.service";

@Component({
  selector: 'app-list-catalogs',
  templateUrl: './list-catalogs.component.html',
  styleUrls: ['./list-catalogs.component.scss']
})
export class ListCatalogsComponent implements OnInit{

  constructor(private titleService: TitleService,
              private footerService: FooterService) {
  }
  ngOnInit(): void {
    this.footerService.enablePagination(false);
    this.titleService.showTitleMsg('List of catalogs', '', false);
  }
}
