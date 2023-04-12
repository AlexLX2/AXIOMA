import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {Company} from "../../../../interfaces/company";
import {User} from "../../../../interfaces/user";
import {UserService} from "../../../../services/user.service";
import {Router} from "@angular/router";
import {TitleService} from "../../../../services/title.service";
import {FooterService} from "../../../../services/footer.service";
import {AlertService} from "../../../../_alert";
import {CompanyService} from "../../../../services/company.service";
import {Role} from "../../../../interfaces/role";

@Component({
    selector: 'app-create-role',
    templateUrl: './create-role.component.html',
    styleUrls: ['./create-role.component.scss']
})
export class CreateRoleComponent implements OnInit {
    vForm: FormGroup;
    selectedUserList: User[] = [];

    allUserList: User[] = [];
    availableUserList: User[] = [];
    companyList: Company[] = [];

    constructor(private userService: UserService,
                private titleService: TitleService,
                private footerService: FooterService,
                private fb: FormBuilder,
                private router: Router,
                private alertService: AlertService,
                private companyService: CompanyService) {

        this.vForm = fb.group({
            name: new FormControl('', Validators.required),
            company: new FormControl('', Validators.required)
        })

    }

    ngOnInit(): void {
        this.footerService.enablePagination(false);
        this.titleService.showTitleMsg('Create role', '', false);
        this.companyService.getAllCompanies().subscribe(companies => {
            this.companyList = companies;
        });
        this.userService.getAllUsers().subscribe(users => {
            this.allUserList = users;
            this.availableUserList = users;
        })

    }

    compareObjects(obj1: any, obj2: any) {
        return obj1 && obj2 ? obj1.id === obj2.id : obj1 === obj2;
    }

    updateAvailableOptions() {
        this.availableUserList = this.allUserList.filter(user =>
            !this.selectedUserList.some(selectedUser =>
                this.compareObjects(user, selectedUser)));
    }

    createRole() {
        const newRole: Role = {
            id: 0,
            name: this.vForm.get('name')?.value,
            company: this.vForm.get('company')?.value
        }

        let message: string = ''

        this.userService.createRole(newRole).subscribe(response => {
            message = 'Role succesfully created with ID-' + response.result;
            console.log('newRole', newRole);
            newRole.id = response.result;
            const roleWithUsers: any =  {
                id: newRole.id,
                role: newRole.name,
                users: this.selectedUserList
            }
            this.userService.addUsersByRole(roleWithUsers).subscribe(data => {
                message = message + data.result;
                this.alertService.success(message, {
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
