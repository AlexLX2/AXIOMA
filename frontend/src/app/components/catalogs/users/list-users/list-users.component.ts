import {Component, OnInit} from '@angular/core';
import {TitleService} from "../../../../services/title.service";
import {UserService} from "../../../../services/user.service";
import {User} from "../../../../interfaces/user";

@Component({
  selector: 'app-list-users',
  templateUrl: './list-users.component.html',
  styleUrls: ['./list-users.component.scss']
})
export class ListUsersComponent implements OnInit {
  userList: User[] = [];

  constructor(private userService: UserService,
              private titleService: TitleService) {
  }
  ngOnInit(): void {
    this.initData();
    this.titleService.showTitleMsg('User list', '', false);
  }

  private initData() {
    this.userService.getAllUsers().subscribe(data => {
      console.log('data', data)
      // for(let item of data) {
      //     this.userList.push(item);
      // }
      this.userList = data;
      console.log('data', data)
    })

    console.log('empl list: ', this.userList);
  }

  editUser(login: string) {
    console.log('login', login);
    //TODO add edit user code
  }
}
