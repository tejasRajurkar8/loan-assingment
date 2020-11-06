import { Component, OnInit } from '@angular/core';
import { Loan } from '../../models/Loan';
import {Schedule} from '../../models/Schedule';
import { LoanApiService } from '../../services/loan-api.service';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';

@Component({
  selector: 'app-view-loan',
  templateUrl: './view-loan.component.html',
  styleUrls: ['./view-loan.component.css']
})
export class ViewLoanComponent implements OnInit {
  loans: Loan[];
  schedules: Schedule[];
  //custId: number = -1;
  viewLoanForm: FormGroup;
  submitted: boolean = false;
  loanId: number;
  displayLoan: boolean;
  displaySchedule: boolean;

  constructor(private loanApiService: LoanApiService, private formBuilder: FormBuilder,private router: Router) { }

  ngOnInit(): void {
    this.viewLoanForm = this.formBuilder.group({
      custId: ["", Validators.required],
    })
  }

  onSubmit() {
    this.displayLoan = false;
    this.displaySchedule = false;
    //this.router.navigate(['viewLoanPage']);
    this.loanApiService.getLoans(this.viewLoanForm.value.custId).subscribe(response => {
      console.log(response);
      if(response.length > 0)
      this.displayLoan = true;
      else
      alert("No records Found.");
      this.loans = <Loan[]>response;
      this.viewLoanForm.reset;
    });
  }

  getSchedule(loanId: number){
    this.displaySchedule = true;
    this.loanApiService.getSchedule(loanId).subscribe(response => {
      console.log(response);
      this.schedules = <Schedule[]>response;
    });
  }

}
