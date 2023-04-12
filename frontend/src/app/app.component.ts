import {AfterViewInit, Component, OnInit} from '@angular/core';
import { SidebarService } from './components/shared/sidebar/sidebar.service';
import {LoaderService} from "./services/loader.service";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements AfterViewInit, OnInit{
  public theme = "dark";
  loaderActive = false;

  constructor(public sidebarService: SidebarService,
              private loaderService: LoaderService) {

  }

  ngAfterViewInit(): void {
    this.loaderService.loading.asObservable().subscribe(async value => {
      this.loaderActive = await value;
    });
  }

  ngOnInit(): void {
    this.loaderActive = false;
  }
}
