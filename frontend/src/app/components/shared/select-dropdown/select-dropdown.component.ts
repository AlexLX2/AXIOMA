import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {Catalog} from "../../../interfaces/catalog";
import {CatalogService} from "../../../services/catalog.service";

@Component({
  selector: 'app-select-dropdown',
  templateUrl: './select-dropdown.component.html',
  styleUrls: ['./select-dropdown.component.scss']
})
export class SelectDropdownComponent implements OnInit{

  @Input() catalogName: string = '';
  @Input() isDropdownOpened: boolean = false;
  itemList: Catalog[] = [];
  currentItem?: Catalog;

  @Output() selectItem = new EventEmitter<any>();
  //isDropdownOpened: boolean = false;

  constructor(private catalogService: CatalogService) {

  }

  ngOnInit(): void {
    // console.log('catalog', this.catalogName);
    this.catalogService.getCatalogItemList(this.catalogName).subscribe(data => {
      // console.log('data', data);
      this.itemList = data;


    })
    console.log('curr', this.currentItem)
    }


  openDropdowns() {
    this.isDropdownOpened = !this.isDropdownOpened;
  }

  select(selectedItem: Catalog) {
    console.log('click!');
    //this.openDropdowns();
    this.currentItem = selectedItem;

    const result = {
      type: this.catalogName,
      value: this.selectItem
    }

    this.selectItem.emit(result);
  }
}
