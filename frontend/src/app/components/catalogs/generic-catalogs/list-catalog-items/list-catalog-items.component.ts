import {Component, OnInit} from '@angular/core';
import {TitleService} from "../../../../services/title.service";
import {FooterService} from "../../../../services/footer.service";
import {CatalogService} from "../../../../services/catalog.service";
import {ActivatedRoute} from "@angular/router";
import {Catalog} from "../../../../interfaces/catalog";
import {AlertService} from "../../../../_alert";

@Component({
  selector: 'app-edit-catalog-item',
  templateUrl: './list-catalog-items.component.html',
  styleUrls: ['./list-catalog-items.component.scss']
})
export class ListCatalogItemsComponent implements OnInit{

  currentCatalog: string = '';
  public isCollapsed = true;
  newItem: string = '';


  catalogItemList: Catalog[] = [];
  constructor(private titleService: TitleService,
              private footerService: FooterService,
              private catalogService: CatalogService,
              private alertService: AlertService,
              private route: ActivatedRoute) {
  }

  ngOnInit(): void {
    this.currentCatalog = this.route.snapshot.params['catalogName'];
    this.titleService.showTitleMsg(this.currentCatalog.toUpperCase(),'', false);
    this.footerService.enablePagination(false);
    this.initData();
  }

  initData() {
    this.catalogService.getCatalogItemList(this.currentCatalog).subscribe(value => {
      console.log('values', value);
      this.catalogItemList = value;
    });
  }

  createItem() {
      console.log('new item', this.newItem);
      this.catalogService.createCatalogItem(this.newItem,this.currentCatalog).subscribe( data => {
        console.log('data', data);
        this.alertService.success('Catalog item created! ' + JSON.stringify(data), {autoClose:true});
        this.initData();
      }, error => {
        this.alertService.error('Catalog item was not created!!! ' + error);
      });
      this.isCollapsed = true;
  }
}
