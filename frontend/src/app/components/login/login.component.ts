import {Component, OnInit} from '@angular/core';
import {AuthService} from "../../services/auth.service";
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {ActivatedRoute, Router} from "@angular/router";
import {StorageService} from "../../services/storage.service";
import {Login} from "../../interfaces/login";

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
              private router: Router) {
    this.loginForm = fb.group({
      login: new FormControl('', [Validators.required, Validators.min(2), Validators.max(30)]),
      password: new FormControl('', [Validators.required, Validators.min(8), Validators.max(30)])
    })
  }

  ngOnInit(): void {
    this.returnUrl = this.route.snapshot.queryParams['returnUrl'] || '/';
  }
  login() {
    this.userLogin.login = this.loginForm.get('login')?.value;
    this.userLogin.password = this.loginForm.get('password')?.value;

    if(!this.loginForm.invalid) {
      this.authService.login(this.userLogin).subscribe(data =>{
        if(!data['jwt-token']){
          console.error('Auth error:', data.message)
      } else {
          this.storageService.addItem('token', data['jwt-token'])
          this.router.navigateByUrl(this.returnUrl);
        }
      });
    }
  }


}
