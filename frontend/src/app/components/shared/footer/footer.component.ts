import {Component, Input, OnInit} from '@angular/core';
import {FooterService} from "../../../services/footer.service";

@Component({
  selector: 'axioma-footer',
  templateUrl: './footer.component.html',
  styleUrls: ['./footer.component.scss']
})
export class FooterComponent implements OnInit{
  enablePagination: boolean = false;

  constructor(private footerService: FooterService) {

  }

  ngOnInit(): void {
    this.footerService.showPagination.asObservable().subscribe(value => {
      this.enablePagination = value;
    })
  }
}
