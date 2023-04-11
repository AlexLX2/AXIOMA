import {Component, OnInit} from '@angular/core';
import {UserService} from "../../../../services/user.service";
import {User} from "../../../../interfaces/user";
import {ActivatedRoute, Router} from "@angular/router";
import {TitleService} from "../../../../services/title.service";
import {FooterService} from "../../../../services/footer.service";
import {FormBuilder, FormGroup} from "@angular/forms";
import {AlertService} from "../../../../_alert";

interface RoleWithUsers {
    role: string,
    users: {login: string}[]
}

@Component({
    selector: 'app-edit-role',
    templateUrl: './edit-role.component.html',
    styleUrls: ['./edit-role.component.scss']
})
export class EditRoleComponent implements OnInit {

    currentRole: string = '';

    selectedUserList: {
        id: number,
        login: string
    }[] = [];

    allUserList: User[] = [];
    availableUserList: User[] = [];
    vForm: FormGroup = new FormGroup<any>('');

    constructor(private userService: UserService,
                private route: ActivatedRoute,
                private titleService: TitleService,
                private footerService: FooterService,
                private fb: FormBuilder,
                private router: Router,
                private alertService: AlertService) {


        this.vForm = this.fb.group({
            roleName: '',
            userList: []
        });

    }

    editRole() {
        console.log('userlist ', this.selectedUserList);

        const newRole: RoleWithUsers = {
            role: this.currentRole,
            users: this.selectedUserList
        }

        console.log('roleusers', newRole);

        this.userService.addUsersByRole(newRole).subscribe(data => {
            console.log('data', data);
            this.alertService.success(data.result, {
                keepAfterRouteChange: true,
                autoClose: true
            });
            this.router.navigateByUrl('/roles');
        }, error => {
            this.alertService.error(error, {
                keepAfterRouteChange: true,
                autoClose: true
            });
            this.router.navigateByUrl('/roles');
        });

    }

    compareObjects(obj1: any, obj2: any) {
        return obj1 && obj2 ? obj1.id === obj2.id : obj1 === obj2;
    }

    ngOnInit(): void {
        this.footerService.enablePagination(false);
        this.titleService.showTitleMsg('Edit role', 'Do not touch if you do not understand!', false)
        this.userService.getAllUsers().subscribe(data => {
            this.allUserList = data;


            this.route.params.subscribe(data => {
                this.currentRole  = data['id'];
                this.userService.getUsersByRole(data['id']).subscribe(users => {
                    this.selectedUserList = users[data['id']]
                        .map(({id, login}: { id: number, login: string }) => ({ id: parseInt(String(id)), login}));
                     // console.log('roleuserlist', this.selectedUserList);
                    this.availableUserList = this.allUserList.filter(user => {
                        return !this.selectedUserList.some(user2 => user.id===user2.id);
                    })
                })
            });
        });
    }

    updateAvailableOptions() {
        this.availableUserList = this.allUserList.filter(user =>
            !this.selectedUserList.some(selectedUser =>
                this.compareObjects(user, selectedUser)));
    }
}
