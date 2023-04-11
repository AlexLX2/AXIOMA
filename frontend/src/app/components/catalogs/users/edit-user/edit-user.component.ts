import {Component, OnInit} from '@angular/core';
import {AlertService} from "../../../../_alert";
import {TitleService} from "../../../../services/title.service";
import {FooterService} from "../../../../services/footer.service";
import {UserService} from "../../../../services/user.service";
import {ActivatedRoute, Router} from "@angular/router";
import {User} from "../../../../interfaces/user";
import {FormArray, FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {Role} from "../../../../interfaces/role";
import {UserRole} from "../../../../interfaces/user-role";

@Component({
    selector: 'app-edit-user',
    templateUrl: './edit-user.component.html',
    styleUrls: ['./edit-user.component.scss']
})
export class EditUserComponent implements OnInit {

    currentUser?: User;
    allRoleList: Role[] = [];
    userRoleList: Role[] = [];

    vForm: FormGroup;

    constructor(private alertService: AlertService,
                private titleService: TitleService,
                private footerService: FooterService,
                private userService: UserService,
                private route: ActivatedRoute,
                private fb: FormBuilder,
                private router: Router) {

        this.vForm = fb.group({
                lastName: new FormControl(this.currentUser?.lastName, [Validators.min(2)]),
                firstName: new FormControl(this.currentUser?.firstName, [Validators.min(2)]),
                login: new FormControl(this.currentUser?.login, [Validators.min(2), Validators.max(30)]),
                email: new FormControl(this.currentUser?.email, [Validators.email]),
                roles: new FormGroup({})
            }
        );

        this.route.params.subscribe(value => {
            this.userService.getUserById(value['id']).subscribe(data => {
                this.currentUser = data;
                this.userRoleList = data['roles'];
                console.log('data', data);

                this.userService.getAllRoles().subscribe(data => {
                    this.allRoleList = data;
                    console.log('all role', this.allRoleList);
                    console.log('user role', this.userRoleList);
                    this.allRoleList.forEach((role) => {
                        const isChecked = this.userRoleList.some(r=> r.id === role.id);
                        const control = new FormControl(isChecked);
                        console.log('control', control);
                        (this.vForm.get('roles') as FormGroup).addControl(role.name, control);
                    });
                    console.log('vform', this.vForm);

                    this.initForm();
                });
            });
        });


        console.log('current user', this.currentUser);


    }

    initForm() {
        this.vForm.get('firstName')?.setValue(this.currentUser?.firstName);
        this.vForm.get('lastName')?.setValue(this.currentUser?.lastName);
        this.vForm.get('email')?.setValue(this.currentUser?.email);
        this.vForm.get('login')?.setValue(this.currentUser?.login);
    }

    ngOnInit(): void {
        this.titleService.showTitleMsg("Edit user", "", false);
        this.footerService.enablePagination(false);
     }

    editUser() {
            const user: User = {
                id: this.currentUser?.id,
                email: this.vForm.get('email')?.value,
                firstName: this.vForm.get('firstName')?.value,
                lastName: this.vForm.get('lastName')?.value,
                login: this.vForm.get('login')?.value
            }

            console.log('user: ', user);

            this.userService.updateUser(user).subscribe(data => {
                // this.router.navigateByUrl('/users');
                this.alertService.success("User updated! " + data.result, {
                    keepAfterRouteChange: true,
                    autoClose: true
                });

                const roles: Role[] = this.vForm.get('roles')?.value;

                const userRoles: UserRole = {
                    user: this.vForm.get('login')?.value,
                    role: Object.entries(roles).filter(([, value]) => value).map(([key, value]) => ({name:key}))
                };

                console.log('userroles', userRoles);

                this.userService.addRolesByUser(userRoles).subscribe(data => console.log('userrole creation', data));
                this.router.navigateByUrl('/users');
            }, error => {
                this.alertService.error("User update failed! " + error, {
                    keepAfterRouteChange:true,
                    autoClose: true
                })
            })


    }

    onRoleChange(event: any) {
        const checkArray: FormArray = this.vForm.get('roles') as FormArray;
        if (event.target.checked) {
            checkArray.push(new FormControl(event.target.value));
        } else {
            let i: number = 0;
            checkArray.controls.forEach((item: any) => {
                if (item.value == event.target.value) {
                    checkArray.removeAt(i);
                    return;
                }
                i++;
            });
        }
    }
}
