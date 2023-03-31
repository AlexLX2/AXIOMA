import {Component, OnInit} from '@angular/core';
import {EmployeeService} from "../../../services/employee.service";
import {Employee} from "../../../interfaces/employee";

@Component({
  selector: 'app-list-users',
  templateUrl: './list-users.component.html',
  styleUrls: ['./list-users.component.scss']
})
export class ListUsersComponent implements OnInit {
  emplList: Employee[] = [];

  constructor(private emplService: EmployeeService) {
  }
  ngOnInit(): void {
    this.initData();
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
