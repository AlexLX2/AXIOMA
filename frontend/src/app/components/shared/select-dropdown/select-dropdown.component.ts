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
    this.catalogService.getCatalogItemList(this.catalogName).subscribe(data => {
      this.itemList = data;
    })
    }

  select(selectedItem: any) {

    this.currentItem = this.itemList.find( s => {
      return s.id == selectedItem.target.value
    });

    const result = {
      type: this.catalogName,
      value: this.currentItem
    }

    this.selectItem.emit(result);
  }
}
