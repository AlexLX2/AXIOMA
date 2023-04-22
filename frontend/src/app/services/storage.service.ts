import {Injectable} from '@angular/core';
import {JwtHelperService} from "@auth0/angular-jwt";
import {User} from "../interfaces/user";

@Injectable({
  providedIn: 'root'
})
export class StorageService {

  constructor(private jwtHelper: JwtHelperService) {
  }


  decodeToken(token: string): User {
    window.localStorage.clear();
    const decodedToken = this.jwtHelper.decodeToken(token);
    this.addItem('token', token);
    return decodedToken;
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
