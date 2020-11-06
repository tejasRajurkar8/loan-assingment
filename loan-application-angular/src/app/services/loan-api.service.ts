import { Injectable } from '@angular/core';
import {Loan} from '../models/Loan';
import {Schedule} from '../models/Schedule';
import {BankEmployee} from '../models/BankEmployee'
import { HttpClient, HttpHeaders } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class LoanApiService {

  currentUser: string = null;
  isLoggedIn: boolean = null;

  constructor(private http:HttpClient) { }

  authenticate(employeeName: string, password: string){
    let basicAuthHeaderString = this.createBasicAuthenticationHttpHeader();
    let headers = new HttpHeaders({
      Authorization : basicAuthHeaderString
    })
    return this.http.get(`http://localhost:8080/authenticate/${employeeName}/${password}`);

  }

  getLoans(custId: number){
    return this.http.get<Loan[]>(`http://localhost:8080/loans/${custId}`);
  }

  createLoan(loan: Loan){
    return this.http.post(`http://localhost:8080/loans/add`, loan);
  }

  getSchedule(loanId: number){
    return this.http.get<Schedule[]>(`http://localhost:8080/schedules/${loanId}`);
  }

  createBasicAuthenticationHttpHeader(){
    let username = "finzly";
    let password = "finzly2020";
    let basicAuthHeaderString = 'Basic ' + window.btoa(username + ':' + password);
    return basicAuthHeaderString;
  }
}
