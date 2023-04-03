import {Injectable, Input} from '@angular/core';
import {BehaviorSubject} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class FooterService {

  public showPagination: BehaviorSubject<boolean>;

  constructor() {
    this.showPagination = new BehaviorSubject<boolean>(false);
  }

  enablePagination(enable: boolean) {
    this.showPagination.next(enable);
  }

}
