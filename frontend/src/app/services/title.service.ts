import { Injectable } from '@angular/core';
import {BehaviorSubject} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class TitleService {
  public title: BehaviorSubject<string>;
  public subTitle: BehaviorSubject<string>;
  public showSearch: BehaviorSubject<boolean>;

  constructor() {
    this.title = new BehaviorSubject<string>('');
    this.subTitle = new BehaviorSubject<string>('');
    this.showSearch = new BehaviorSubject<boolean>(false);
  }

  showTitleMsg(titleMsg: string, subtitleMsg: string, showSearch: boolean) {
    this.title.next(titleMsg);
    this.subTitle.next(subtitleMsg);
    this.showSearch.next(showSearch);
  }
}
