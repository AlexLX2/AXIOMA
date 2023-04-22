import {Injectable} from '@angular/core';
import {Observable} from "rxjs";
import {HttpClient} from "@angular/common/http";
import {StorageService} from "./storage.service";
import {JwtHelperService} from "@auth0/angular-jwt";
import {environment} from "../../environments/environment";
import {Login} from "../interfaces/login";
import {User} from "../interfaces/user";

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  baseUrl: string = environment.baseUrl;

  constructor(private http: HttpClient,
              private storageService: StorageService,
               private jwtHelper: JwtHelperService
  ) { }

  login(login: Login): Observable<any> {
    return this.http.post<any>(`${this.baseUrl}/auth/login` , login);
  }

  logout() {
    this.storageService.clean();
  }

  isAuthenticated(): boolean {
     const token = this.storageService.getItem('token');
     return token !== null && !this.jwtHelper.isTokenExpired(token);
  }

  getCurrenUserInfo(): User {
    const user: User = {
      email : this.storageService.getItem('email'),
      firstName : this.storageService.getItem('firstName'),
      lastName : this.storageService.getItem('lastName'),
      login : this.storageService.getItem('login')
    }
    return user;
    };

  isAdmin(): boolean {
    const roles = this.storageService.getItem('roles');
    return roles.includes('ROLE_ADMIN1');
  }
}
