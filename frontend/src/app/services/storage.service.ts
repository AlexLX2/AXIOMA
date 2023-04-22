import { Injectable } from '@angular/core';
import {JwtHelperService} from "@auth0/angular-jwt";

@Injectable({
  providedIn: 'root'
})
export class StorageService {

  constructor(private jwtHelper: JwtHelperService) {
  }


  decodeToken(token: string) {
    window.localStorage.clear();
    const decodedToken = this.jwtHelper.decodeToken(token);
    this.addItem('lastName', decodedToken.lastName);
    this.addItem('firstName', decodedToken.firstName);
    this.addItem('email', decodedToken.email);
    this.addItem('roles', decodedToken.roles);
    this.addItem('token', token);
    this.addItem('login', decodedToken.login);
  }

  addItem(key: string, value: any) {
    window.localStorage.setItem(key, value);
  }

  getItem(key: string) :string {
    let value: string = '';

    if (window.localStorage.getItem(key) != null) {
      value = window.localStorage.getItem(key) as string;
    };

    return value;
  }

  clean() {
    window.localStorage.clear();
  }
}
