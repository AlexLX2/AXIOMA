import { Injectable } from '@angular/core';
import {environment} from "../../environments/environment";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Employee} from "../interfaces/employee";
import {User} from "../interfaces/user";

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private baseUrl: string = environment.baseUrl;

  constructor(private http: HttpClient) { }

  getAllUsers(): Observable<Employee[]>{
    return this.http.get<Employee[]>(`${this.baseUrl}/api/users/all`);
  }

  getUserByName(partialName: string): Observable<any[]> {
    return this.http.get<any[]>(`${this.baseUrl}/api/users/searchByName?partialName=${partialName}`
    );
  }

  createUser(user: User): Observable<any> {
    return this.http.post(`${this.baseUrl}/api/users/new`, user);
  }

}
