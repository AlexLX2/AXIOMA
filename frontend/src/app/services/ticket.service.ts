import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {environment} from "../../environments/environment";
import {Ticket} from "../interfaces/ticket";
import {BackendResponse} from "../interfaces/backend-response";

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

  createTicket(ticket: Ticket): Observable<BackendResponse> {
    return this.http.post<BackendResponse>(`${this.baseUrl}/api/tickets/new`, ticket);
  }

  createTicketHeader(ticket: Ticket): Observable<any> {
    return this.http.post(`${this.baseUrl}/api/tickets/createTicketHeader`, ticket);
  }
}
