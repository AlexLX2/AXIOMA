import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {environment} from "../../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class TicketService {

  private baseUrl: string = environment.baseUrl;

  constructor(private http: HttpClient) { }

  getAllTickets(): Observable<any> {
    return this.http.get(`${this.baseUrl}/api/tickets/all`);
  }

  getTicketById(id: number): Observable<any> {
    return this.http.get(`${this.baseUrl}/api/tickets/${id}`);
  }
}
