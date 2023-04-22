import {Component, OnInit} from '@angular/core';
import {FormBuilder} from '@angular/forms';
import {ActivatedRoute, Router} from '@angular/router';
import {SidebarService, themeTypes} from './sidebar.service';
import {AuthService} from "../../../services/auth.service";
import {User} from "../../../interfaces/user";

@Component({
    selector: 'axioma-sidebar',
    templateUrl: './sidebar.component.html',
    styleUrls: ['./sidebar.component.scss'],
})
export class SidebarComponent implements OnInit {
    public isOpenedProfileDropdown = false;
    public isOpenedConfDropdown = false;
    public isSideMenuOpened: boolean = false;
    currentUser: User = {email: "", firstName: "", lastName: "", login: ""};
    public themeForm = this.fb.group({
        theme: [this.sidebarService.theme.value === themeTypes.white],
    });

    constructor(
        private fb: FormBuilder,
        private route: ActivatedRoute,
        private sidebarService: SidebarService,
        private authService: AuthService,
        private router: Router
    ) {
        this.themeForm.valueChanges.subscribe(({theme}) => {
            const newTheme = theme ? themeTypes.white : themeTypes.dark;
            this.sidebarService.switchTheme(newTheme);
        });
    }

    public toggleProfileDropdown = () =>
        (this.isOpenedProfileDropdown = !this.isOpenedProfileDropdown);

    public toggleConfDropdown = () =>
        (this.isOpenedConfDropdown = !this.isOpenedConfDropdown);

    toggleSideMenu() {
        this.isSideMenuOpened = !this.isSideMenuOpened
    }

    logout() {
        this.authService.logout();
        this.router.navigateByUrl('/login');
    }

    isAdmin() {
        return this.currentUser.roles?.includes('ROLE_ADMIN');
    }

    isAuthenticated() {
        return this.authService.isAuthenticated();
    }

    ngOnInit(): void {
        this.authService.getCurrenUserInfo().subscribe(user => {
            this.currentUser = user;
        })
    }
}
