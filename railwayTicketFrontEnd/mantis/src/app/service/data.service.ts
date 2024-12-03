// src/app/data.service.ts
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class DataService {
  //private apiUrl = 'http://localhost:8083/trains'; // Replace with your API URL
  //requestData: any;

  constructor(private http: HttpClient) {}

  //Get method to fetch data from any endpoint
  fetchData(apiUrl: string): Observable<any> {
    return this.http.get<any>(apiUrl);
  }

  //Post method to send data to any endpoint
  postData(apiUrl: string,requestData: any): Observable<any> {
    //const loginData = { username, password };
    return this.http.post(apiUrl, requestData);
  }

  //Put method to send data to any endpoint
  updateData(apiUrl: string,requestData: any): Observable<any> {
    //const loginData = { username, password };
    return this.http.put(apiUrl, requestData);
  }

  //Delete method to send data to any endpoint
  deleteData(apiUrl: string): Observable<any> {
    //const loginData = { username, password };
    return this.http.delete(apiUrl);
  }
}
// export class DataService {
//   private apiUrl = 'http://localhost:8083/trains'; // Replace with your API URL

//   constructor(private http: HttpClient) {}

//   fetchData(): Observable<any> {
//     return this.http.get<any>(this.apiUrl);
//   }
// }