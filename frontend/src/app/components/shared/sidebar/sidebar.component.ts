import { Component } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import {ActivatedRoute, Router} from '@angular/router';
import { SidebarService, themeTypes } from './sidebar.service';
import {AuthService} from "../../../services/auth.service";

@Component({
  selector: 'axioma-sidebar',
  templateUrl: './sidebar.component.html',
  styleUrls: ['./sidebar.component.scss'],
})
export class SidebarComponent {
  public isOpenedProfileDropdown = false;
  public isOpenedConfDropdown = false;
  public isSideMenuOpened: boolean = false;

  public toggleProfileDropdown = () =>
    (this.isOpenedProfileDropdown = !this.isOpenedProfileDropdown);
  public toggleConfDropdown = () =>
    (this.isOpenedConfDropdown = !this.isOpenedConfDropdown);

  constructor(
    private fb: FormBuilder,
    private route: ActivatedRoute,
    private sidebarService: SidebarService,
    private authService: AuthService,
    private router: Router
  ) {
    this.themeForm.valueChanges.subscribe(({ theme }) => {
      const newTheme = theme ? themeTypes.white : themeTypes.dark;
      this.sidebarService.switchTheme(newTheme);
    });
  }

  public themeForm = this.fb.group({
    theme: [this.sidebarService.theme.value === themeTypes.white],
  });


  toggleSideMenu() {
    this.isSideMenuOpened = !this.isSideMenuOpened
  }

    logout() {
        this.authService.logout();
        this.router.navigateByUrl('/login');
    }
}
