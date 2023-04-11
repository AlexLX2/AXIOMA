import {Component, OnInit} from '@angular/core';
import {Company} from "../../../../interfaces/company";
import {AlertService} from "../../../../_alert";
import {TitleService} from "../../../../services/title.service";
import {FooterService} from "../../../../services/footer.service";
import {CompanyService} from "../../../../services/company.service";

@Component({
  selector: 'app-list-company',
  templateUrl: './list-company.component.html',
  styleUrls: ['./list-company.component.scss']
})
export class ListCompanyComponent implements OnInit{

  companyList: Company[] = [];

  constructor(private alertService: AlertService,
              private titleService: TitleService,
              private footerService: FooterService,
              private companyService: CompanyService) {

    this.titleService.showTitleMsg('Company list', '', false);
    this.footerService.enablePagination(false);
  }

  ngOnInit(): void {
    this.companyService.getAllCompanies().subscribe(data => {
      this.companyList = data;
    })
  }

}
