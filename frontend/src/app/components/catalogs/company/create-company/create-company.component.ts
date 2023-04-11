import { Component } from '@angular/core';
import {TitleService} from "../../../../services/title.service";
import {FooterService} from "../../../../services/footer.service";
import {AlertService} from "../../../../_alert";
import {CompanyService} from "../../../../services/company.service";
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {Company} from "../../../../interfaces/company";
import {Router} from "@angular/router";

@Component({
  selector: 'app-create-company',
  templateUrl: './create-company.component.html',
  styleUrls: ['./create-company.component.scss']
})
export class CreateCompanyComponent {
  vForm: FormGroup;

  constructor(private titleService: TitleService,
              private footerService: FooterService,
              private alertService: AlertService,
              private companyService: CompanyService,
              private fb: FormBuilder,
              private router: Router) {
    this.vForm = fb.group({
      name: new FormControl('', Validators.required),
      address: new FormControl(''),
      url: new FormControl(''),
      comments: new FormControl('')
    });
  }
  createCompany() {
        console.log('vform', this.vForm.value);
        const company:Company = this.vForm.value;
        this.companyService.createCompany(company).subscribe(data => {
          this.alertService.success(data.result, {
              keepAfterRouteChange: true,
              autoClose: true
          });
          this.router.navigateByUrl('/companies');
        }, error => {
            this.alertService.error(error.result, {
                keepAfterRouteChange: true,
                autoClose: true
            })
        })
  }
}
