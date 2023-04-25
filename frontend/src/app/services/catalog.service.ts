import { Injectable } from '@angular/core';
import {environment} from "../../environments/environment";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Catalog} from "../interfaces/catalog";

@Injectable({
  providedIn: 'root'
})
export class CatalogService {

  baseUrl: string = environment.baseUrl;

  constructor(private http: HttpClient) { }

  getCatalogItemList(catalog: string): Observable<Catalog[]> {
    catalog = catalog.toLowerCase();
    return this.http.get<Catalog[]>(`${this.baseUrl}/${catalog}`);
  }

  editCatalogItem(catalog:string, catalogItem: Catalog): Observable<any> {
    return this.http.patch(`${this.baseUrl}/${catalog}/update`, catalogItem);
  }

  createCatalogItem(item: string, catalogType: string) {
    const catalogItem: Catalog = {
      id:0,
      name:item
    }
    return this.http.post(`${this.baseUrl}/${catalogType}/new`, catalogItem);
  }

  getCatalogItemById(catalogName: string, id: number): Observable<Catalog> {
    return  this.http.get<Catalog>(`${this.baseUrl}/${catalogName}/${id}`);
  }
}
