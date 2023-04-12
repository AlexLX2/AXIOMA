import {Component, OnInit} from '@angular/core';
import {AlertService} from "../../../../_alert";
import {CompanyService} from "../../../../services/company.service";
import {TitleService} from "../../../../services/title.service";
import {FooterService} from "../../../../services/footer.service";
import {ActivatedRoute, Router} from "@angular/router";
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {Company} from "../../../../interfaces/company";
import {LoaderService} from "../../../../services/loader.service";

@Component({
  selector: 'app-edit-company',
  templateUrl: './edit-company.component.html',
  styleUrls: ['./edit-company.component.scss']
})
export class EditCompanyComponent implements OnInit{

  vForm: FormGroup;
  currentCompany?: Company;
  constructor(private alertService: AlertService,
              private companyService: CompanyService,
              private titleService: TitleService,
              private footerService: FooterService,
              private route: ActivatedRoute,
              private router: Router,
              private fb: FormBuilder) {
    this.titleService.showTitleMsg("Edit company", '', false);
    this.footerService.enablePagination(false);

    this.vForm = fb.group({
      name: new FormControl('', Validators.required),
      address: new FormControl(''),
      url: new FormControl(''),
      comments: new FormControl('')
    })
  }

  ngOnInit(): void {
    this.route.params.subscribe(data => {
      this.companyService.getCompanyById(data['id']).subscribe(comp =>{
        this.currentCompany = comp;
        this.initForm();
      })
    });
  }


  editCompany() {

    const comp: Company = {
      id: this.currentCompany?.id,
      ...this.vForm.value
    }

      this.companyService.updateCompany(comp).subscribe(data => {
        this.alertService.success(data.result, {
          keepAfterRouteChange: true,
          autoClose: true
        });
        this.router.navigateByUrl('/companies')
      }, error =>{
        this.alertService.error(error.result, {
          keepAfterRouteChange:true,
          autoClose: true
        });
      })

  }

  private initForm() {
    this.vForm.get('name')?.patchValue(this.currentCompany?.name);
    this.vForm.get('address')?.patchValue(this.currentCompany?.address);
    this.vForm.get('url')?.patchValue(this.currentCompany?.url);
    this.vForm.get('comments')?.patchValue(this.currentCompany?.comments);
  }
}
