import { Injectable } from '@angular/core';
import {environment} from "../../environments/environment";
import {HttpClient} from "@angular/common/http";
import {map, Observable} from "rxjs";
import {Catalog} from "../interfaces/catalog";

@Injectable({
  providedIn: 'root'
})
export class CatalogService {

  baseUrl: string = environment.baseUrl;

  constructor(private http: HttpClient) { }

  getCatalogItemList(catalog: string): Observable<Catalog[]> {
    catalog = catalog.toLowerCase();
    return this.http.get(`${this.baseUrl}/${catalog}`).pipe(
        map(data=> {
              console.log('data', data)
              return (<any>data)._embedded.catalogueDTOes;
            }
        )
    );
  }

  editCatalogItem(catalog:string, catalogItem: Catalog): Observable<any> {
    return this.http.post(`${this.baseUrl}/${catalog}/createCatalogue`, catalogItem);
  }

  createCatalogItem(item: Catalog, catalogType: string) {
    return this.http.post(`${this.baseUrl}/${catalogType}/new`, item);
  }
}
