import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {environment} from "../../environments/environment";
import {Observable} from "rxjs";
import {Company} from "../interfaces/company";
import {BackendResponse} from "../interfaces/backend-response";

@Injectable({
  providedIn: 'root'
})
export class CompanyService {


  private baseUrl: string = environment.baseUrl;
  constructor(private http: HttpClient) { }

  getAllCompanies(): Observable<Company[]> {
    return this.http.get<Company[]>(`${this.baseUrl}/api/company/all`);
  }

  createCompany(company: Company): Observable<BackendResponse> {
    return this.http.post<BackendResponse>(`${this.baseUrl}/api/company/new`, company);
  }

  updateCompany(company: Company): Observable<BackendResponse> {
    return this.http.patch<BackendResponse>(`${this.baseUrl}/api/company/update/${company.id}`, company);
  }

  getCompanyById(id: number): Observable<Company> {
    return this.http.get<Company>(`${this.baseUrl}/api/company/${id}`);
  }

}
