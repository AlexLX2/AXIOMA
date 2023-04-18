import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {environment} from "../../environments/environment";
import {Ticket} from "../interfaces/ticket";
import {BackendResponse} from "../interfaces/backend-response";
import {TicketBody} from "../interfaces/ticket-body";
import {TicketAttachment} from "../interfaces/ticket-attachment";

@Injectable({
  providedIn: 'root'
})
export class TicketService {

  private baseUrl: string = environment.baseUrl;

  constructor(private http: HttpClient) { }

  getAllTickets(pageSize: number, pagNum: number): Observable<any> {
    return this.http.get(`${this.baseUrl}/api/tickets/all?pageNo=${pagNum-1}&pageSize=${pageSize}`);
  }

  getTicketById(id: number): Observable<any> {
    return this.http.get(`${this.baseUrl}/api/tickets/${id}`);
  }

  createTicket(ticket: Ticket): Observable<BackendResponse> {
    return this.http.post<BackendResponse>(`${this.baseUrl}/api/tickets/new`, ticket);
  }

  createTicketBody(ticketBody: TicketBody): Observable<BackendResponse> {
    return this.http.post<BackendResponse>(`${this.baseUrl}/api/tickets/new_body`, ticketBody);
  }

  createTicketAttachment(bodyId: number, files: File[]): Observable<BackendResponse> {
    const formData: FormData = new FormData();
    files.forEach(file => {
      formData.append('files', file);
    });

    return this.http.post<BackendResponse>(`${this.baseUrl}/api/tickets/new_attachment/?bodyId=${bodyId}`, formData, {
      headers: {}
    });
  }

  getTicketCount(): Observable<number> {
    return this.http.get<number>(`${this.baseUrl}/api/tickets/count`);
  }

  getAttachmentById(id: number): Observable<any> {
    return this.http.get(`${this.baseUrl}/api/tickets/files/${id}`, {responseType: 'blob', observe: 'response'});
  }
}
