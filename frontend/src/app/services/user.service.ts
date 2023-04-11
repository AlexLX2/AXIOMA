import { Injectable } from '@angular/core';
import {environment} from "../../environments/environment";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Employee} from "../interfaces/employee";
import {User} from "../interfaces/user";
import {Role} from "../interfaces/role";
import {UserRole} from "../interfaces/user-role";

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private baseUrl: string = environment.baseUrl;

  constructor(private http: HttpClient) { }

  getAllUsers(): Observable<User[]>{
    return this.http.get<User[]>(`${this.baseUrl}/api/users/all`);
  }

  getUserById(id: number): Observable<any> {
    return this.http.get(`${this.baseUrl}/api/users/id/${id}`);
  }

  getUserByName(partialName: string): Observable<any[]> {
    return this.http.get<any[]>(`${this.baseUrl}/api/users/searchByName?partialName=${partialName}`
    );
  }

  getAllRoles(): Observable<Role[]> {
    return this.http.get<Role[]>(`${this.baseUrl}/api/user_role/get_all_roles/`);
  }

  createUser(user: User): Observable<any> {
    return this.http.post(`${this.baseUrl}/api/users/new`, user)
        .pipe();
  }

  addRolesByUser(userRoles: UserRole): Observable<any> {
    return this.http.post(`${this.baseUrl}/api/user_role/add_roles_by_single_user`, userRoles);
  }

  updateUser(user: User): Observable<any> {
    return this.http.patch(`${this.baseUrl}/api/users/update/${user.id}`, user);
  }

  getUsersByRole(roleName: string): Observable<any> {
    return this.http.get(`${this.baseUrl}/api/user_role/get_by_roles/${roleName}`);
  }

  addUsersByRole(roleUsers: any): Observable<any> {
    return this.http.post(`${this.baseUrl}/api/user_role/add_users_by_single_role`, roleUsers);
  }


}
