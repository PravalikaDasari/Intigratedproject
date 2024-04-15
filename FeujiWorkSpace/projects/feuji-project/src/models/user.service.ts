import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable, tap } from "rxjs";
import { User } from "./user.model";
import { Router } from "@angular/router";
import { Employee } from "./employee.model";

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private apiUrl = 'http://localhost:8082/api';

  constructor(private http: HttpClient,private router: Router) {}

  login(body:any):Observable<any>{
    return this.http.post(`${this.apiUrl}/users/login`,body)
  }

  // login(userEmail: string, userPassword: string):Observable<User>{
  //   const body = { userEmail, userPassword };
  //   console.log(body)
  //   return this.http.post<User>(`${this.apiUrl}/api/users/login`,body)
  // }
  logout() {
    this.router.navigate(['/login']);
  }

  getEmployeeByid(userEmpid:any): Observable<Employee> {
    const url = `${this.apiUrl}/employee/${userEmpid}`;
    console.log("Request URL:", url);
    console.log(userEmpid+"service employee id");
    return this.http.get<Employee>(url);
  }

}