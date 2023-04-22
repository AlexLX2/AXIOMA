import {Component, OnInit} from '@angular/core';
import {AuthService} from "../../services/auth.service";
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {ActivatedRoute, Router} from "@angular/router";
import {StorageService} from "../../services/storage.service";
import {Login} from "../../interfaces/login";
import {AlertService} from "../../_alert";
import {TitleService} from "../../services/title.service";
import {FooterService} from "../../services/footer.service";
import {JwtHelperService} from "@auth0/angular-jwt";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit{

  loginForm: FormGroup;

  userLogin: Login = {login: "", password: ""};

  returnUrl: string = '';


  constructor(private authService: AuthService,
              private storageService: StorageService,
              private fb: FormBuilder,
              private route: ActivatedRoute,
              private router: Router,
              private alertService: AlertService,
              private titleService: TitleService,
              private footerService: FooterService,
              private jwtHelper: JwtHelperService) {
    this.loginForm = fb.group({
      login: new FormControl('', [Validators.required, Validators.min(2), Validators.max(30)]),
      password: new FormControl('', [Validators.required, Validators.min(8), Validators.max(30)])
    })
  }

  ngOnInit(): void {
    this.returnUrl = this.route.snapshot.queryParams['returnUrl'] || '/';
    this.titleService.showTitleMsg('','',false);
    this.footerService.enablePagination(false);
  }
  login() {
    this.userLogin.login = this.loginForm.get('login')?.value;
    this.userLogin.password = this.loginForm.get('password')?.value;

    if(!this.loginForm.invalid) {
      this.authService.login(this.userLogin).subscribe(data =>{
        if(!data['jwt-token']){
          console.error('Auth error:', data.message)
          this.alertService.error(data.message, { autoClose: true});
      } else {
          this.authService.setCurrentUserInfo(this.storageService.decodeToken(data['jwt-token']));
          this.router.navigateByUrl(this.returnUrl);
        }
      });
    }
  }


}
