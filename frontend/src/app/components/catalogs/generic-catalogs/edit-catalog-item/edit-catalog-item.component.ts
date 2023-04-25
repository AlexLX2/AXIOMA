import { Component } from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {AlertService} from "../../../../_alert";
import {TitleService} from "../../../../services/title.service";
import {FooterService} from "../../../../services/footer.service";
import {ActivatedRoute, Router} from "@angular/router";
import {Catalog} from "../../../../interfaces/catalog";
import {CatalogService} from "../../../../services/catalog.service";

@Component({
  selector: 'app-edit-catalog-item',
  templateUrl: './edit-catalog-item.component.html',
  styleUrls: ['./edit-catalog-item.component.scss']
})
export class EditCatalogItemComponent {
  vForm: FormGroup;
  currentItem: Catalog = {id: 0, name: ""};
  catalogName: string = '';
  currentId: number = 0;

  constructor(private alertService: AlertService,
              private catalogService: CatalogService,
              private titleService: TitleService,
              private footerService: FooterService,
              private route: ActivatedRoute,
              private router: Router,
              private fb: FormBuilder) {
    this.titleService.showTitleMsg("Edit catalog item", '', false);
    this.footerService.enablePagination(false);

    this.vForm = fb.group({
      name: new FormControl('', Validators.required)
    })
  }

  ngOnInit(): void {
    this.route.params.subscribe(data => {
      console.log('rotue params',data);
      this.catalogName = data['catalogName'];
      this.currentId = data['id'];

      this.catalogService.getCatalogItemById(this.catalogName, this.currentId).subscribe( item => {
        this.currentItem = item;
        this.initForm();
      });
    });
  }


  editItem() {
      this.currentItem.name = this.vForm.get('name')?.value;

      this.catalogService.editCatalogItem(this.catalogName, this.currentItem).subscribe(data => {
        this.alertService.success(data.result, {keepAfterRouteChange:true, autoClose: true});
        this.router.navigateByUrl('/catalogs/' + this.catalogName);
      })
  }

  private initForm() {
    this.vForm.get('name')?.patchValue(this.currentItem?.name);
   }
}
