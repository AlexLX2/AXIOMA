import {Component, OnInit} from '@angular/core';
import {UserService} from "../../../../services/user.service";
import {User} from "../../../../interfaces/user";
import {ActivatedRoute, Router} from "@angular/router";
import {TitleService} from "../../../../services/title.service";
import {FooterService} from "../../../../services/footer.service";
import {FormBuilder, FormGroup} from "@angular/forms";
import {AlertService} from "../../../../_alert";
import {Role} from "../../../../interfaces/role";

@Component({
    selector: 'app-edit-role',
    templateUrl: './edit-role.component.html',
    styleUrls: ['./edit-role.component.scss']
})
export class EditRoleComponent implements OnInit {

    currentRole?: Role;
    roleName: string = '';

    selectedUserList: {
        id: number,
        login: string
    }[] = [];

    allUserList: User[] = [];
    availableUserList: User[] = [];

    constructor(private userService: UserService,
                private route: ActivatedRoute,
                private titleService: TitleService,
                private footerService: FooterService,
                private router: Router,
                private alertService: AlertService) {

    }

    ngOnInit(): void {
        this.footerService.enablePagination(false);
        this.titleService.showTitleMsg('Edit role', 'Do not touch if you do not understand!', false)
        this.userService.getAllUsers().subscribe(data => {
            this.allUserList = data;


            this.route.params.subscribe(data => {
                this.userService.getRoleById(data['id']).subscribe(role => {
                    console.log('role', role);
                    this.currentRole = role;
                    this.roleName = role.name;
                    this.userService.getUsersByRole(this.roleName).subscribe(users => {
                        console.log('users', users);
                        this.selectedUserList = users[this.roleName]
                            .map(({id, login}: { id: number, login: string }) => ({id: parseInt(String(id)), login}));
                        this.availableUserList = this.allUserList.filter(user => {
                            return !this.selectedUserList.some(user2 => user.id === user2.id);
                        })
                    })
                });

            });
        });
    }

    editRole() {
        console.log('userlist ', this.selectedUserList);

        console.log('currentrole', this.currentRole);

        console.log('rolename', this.roleName);

        if (this.currentRole) {

            const newRole: { role: string; id: number | undefined; users: { id: number; login: string }[] } = {
                id: this.currentRole.id,
                role: this.roleName.replace('ROLE_',''),
                users: this.selectedUserList
            };

            console.log('roleusers', newRole);


            this.currentRole.name = this.roleName.replace('ROLE_','');
            this.userService.updateRole(this.currentRole).subscribe(data => {
                console.log('data', data);
                this.userService.addUsersByRole(newRole).subscribe(data => {
                    this.alertService.success(data.result, {
                        keepAfterRouteChange: true,
                        autoClose: true
                    });
                    this.router.navigateByUrl('/roles');
                }, error => {
                    this.alertService.error(error.result, {
                        keepAfterRouteChange: true,
                        autoClose: true
                    });
                    this.router.navigateByUrl('/roles');
                });
            })
        }


    }

    compareObjects(obj1: any, obj2: any) {
        return obj1 && obj2 ? obj1.id === obj2.id : obj1 === obj2;
    }

    updateAvailableOptions() {
        this.availableUserList = this.allUserList.filter(user =>
            !this.selectedUserList.some(selectedUser =>
                this.compareObjects(user, selectedUser)));
    }

    changeRolename($event: any) {
        console.log('event', $event);
        this.roleName = $event;
    }
}
