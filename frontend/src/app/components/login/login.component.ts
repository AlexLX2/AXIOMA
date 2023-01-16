import {Component, OnInit} from '@angular/core';
import {AuthService} from "../../services/auth.service";
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {User} from "../../interfaces/user";
import {ActivatedRoute, Router} from "@angular/router";
import {StorageService} from "../../services/storage.service";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit{

  loginForm: FormGroup;

  user: User = {login: "", password: ""};

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
    this.user.login = this.loginForm.get('login')?.value;
    this.user.password = this.loginForm.get('password')?.value;

    if(!this.loginForm.invalid) {
      this.authService.login(this.user).subscribe(data =>{
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
