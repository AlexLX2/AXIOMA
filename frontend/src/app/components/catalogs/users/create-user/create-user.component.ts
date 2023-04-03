import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {UserService} from "../../../../services/user.service";
import {User} from "../../../../interfaces/user";
import {PasswordValidator} from "../../../../validators/password.validator";
import {Router} from "@angular/router";
import {AlertService} from "../../../../_alert";
import {TitleService} from "../../../../services/title.service";

@Component({
    selector: 'app-create-user-dialog',
    templateUrl: './create-user.component.html',
    styleUrls: ['./create-user.component.scss']
})
export class CreateUserComponent implements OnInit{
    vForm: FormGroup;

    constructor(private fb: FormBuilder,
                private userService: UserService,
                private router: Router,
                private alertService: AlertService,
                private titleService: TitleService) {
        this.vForm = fb.group({
                lastName: new FormControl('', [Validators.min(2)]),
                firstName: new FormControl('', [Validators.min(2)]),
                login: new FormControl('', [Validators.min(2), Validators.max(30)]),
                password: new FormControl('', Validators.compose([
                    Validators.minLength(8),
                    Validators.required,
                    Validators.pattern('^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])[a-zA-Z0-9]+$') //this is for the letters (both uppercase and lowercase) and numbers validation
                ])),
                confirmPassword: new FormControl('', [Validators.required]),
                email: new FormControl('', [Validators.email]),
            }, {
                validator: [PasswordValidator.MatchValidator('password', 'confirmPassword')]
            }
        );
    }

    ngOnInit(): void {
        this.titleService.showTitleMsg('Create user', '', false);
    }

    get passwordMatchError() {
        return (
            this.vForm.getError('mismatch') &&
            this.vForm.get('confirmPassword')?.touched
        );
    }

    createUser() {
        if (!this.vForm.invalid) {
            const user: User = {
                email: this.vForm.get('email')?.value,
                firstName: this.vForm.get('firstName')?.value,
                lastName: this.vForm.get('lastName')?.value,
                login: this.vForm.get('login')?.value,
                password: this.vForm.get('password')?.value
            }
            this.userService.createUser(user).subscribe(data => {
                console.log('User creation: ', data);
                this.router.navigateByUrl('/users');
                this.alertService.success("User created! " + data.result, {keepAfterRouteChange:true, autoClose: true});
            }, error => {
                this.alertService.error("User creation failed: " + error, {keepAfterRouteChange:true, autoClose: true});
            });
        }
    }

}
