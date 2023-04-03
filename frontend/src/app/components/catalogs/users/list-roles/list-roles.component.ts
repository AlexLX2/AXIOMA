import {Component, OnInit} from '@angular/core';
import {TitleService} from "../../../../services/title.service";
import {FooterService} from "../../../../services/footer.service";
import {UserService} from "../../../../services/user.service";
import {Role} from "../../../../interfaces/role";

@Component({
  selector: 'app-list-roles',
  templateUrl: './list-roles.component.html',
  styleUrls: ['./list-roles.component.scss']
})
export class ListRolesComponent implements OnInit{

  roleList: Role[] = [];
  constructor(private titleService: TitleService,
              private footerService: FooterService,
              private userService: UserService) {
  }

  ngOnInit(): void {
    this.titleService.showTitleMsg('Roles', 'Please do not touch if you do not understand!', false);
    this.footerService.enablePagination(false);
    this.userService.getAllRoles().subscribe(value => {
      console.log('data', value)
      this.roleList = value;
    })
  }

}
