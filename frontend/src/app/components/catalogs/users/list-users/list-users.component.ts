import {Component, OnInit} from '@angular/core';
import {EmployeeService} from "../../../../services/employee.service";
import {Employee} from "../../../../interfaces/employee";
import {TitleService} from "../../../../services/title.service";

@Component({
  selector: 'app-list-users',
  templateUrl: './list-users.component.html',
  styleUrls: ['./list-users.component.scss']
})
export class ListUsersComponent implements OnInit {
  emplList: Employee[] = [];

  constructor(private emplService: EmployeeService,
              private titleService: TitleService) {
  }
  ngOnInit(): void {
    this.initData();
    this.titleService.showTitleMsg('User list', '', false);
  }

  private initData() {
    this.emplService.getAllEmployees().subscribe(data => {
      for(let item of data) {
          this.emplList.push(item);
      }
      this.emplList = data;
      console.log('data', data)
    })

    console.log('empl list: ', this.emplList);
  }
}
