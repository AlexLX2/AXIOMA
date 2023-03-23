import { Injectable } from '@angular/core';
import {HttpClient, HttpParams} from "@angular/common/http";
import {Observable} from "rxjs";
import {Employee} from "../interfaces/employee";
import {environment} from "../../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class EmployeeService {

  private baseUrl: string = environment.baseUrl;

  constructor(private http: HttpClient) { }

  getAllEmployees(): Observable<Employee[]>{
    return this.http.get<Employee[]>(`${this.baseUrl}/api/users/all`);
  }

  getEmployeesByName(partialName: string): Observable<any[]> {
        return this.http.get<any[]>(`${this.baseUrl}/api/users/searchByName?partialName=${partialName}`
    );
  }

}
