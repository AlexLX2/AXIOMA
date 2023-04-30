import { Injectable } from '@angular/core';
import {environment} from "../../environments/environment";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {ConfigItem} from "../interfaces/config-item";
import {BackendResponse} from "../interfaces/backend-response";

@Injectable({
  providedIn: 'root'
})
export class SettingsService {
  baseUrl: string = environment.baseUrl;

  constructor(private http: HttpClient) { }

  getAllSettings(): Observable<ConfigItem[]> {
    return this.http.get<ConfigItem[]>(`${this.baseUrl}/api/settings/all`);
  }

  updateSetting(item: ConfigItem): Observable<BackendResponse> {
    return this.http.patch<BackendResponse>(`${this.baseUrl}/api/settings/update`, item);
  }
}
