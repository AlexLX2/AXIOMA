import { Injectable } from '@angular/core';
import {Observable} from "rxjs";
import {HttpClient} from "@angular/common/http";
import {User} from "../interfaces/user";
import {StorageService} from "./storage.service";
import {JwtHelperService} from "@auth0/angular-jwt";
import {environment} from "../../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  baseUrl: string = environment.baseUrl;

  constructor(private http: HttpClient,
              private storageService: StorageService,
               private jwtHelper: JwtHelperService
  ) { }

  login(user: User): Observable<any> {
    return this.http.post<any>(`${this.baseUrl}auth/login` , user);
  }

  logout() {
    this.storageService.clean();
  }

  isAuthenticated(): boolean {
     const token = this.storageService.getItem('token');
     return token !== null && !this.jwtHelper.isTokenExpired(token);
  }
}
