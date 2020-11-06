import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms'
import { ActivatedRoute, Router } from '@angular/router';
import {Loan} from '../../models/Loan';
import {LoanApiService} from '../../services/loan-api.service';

@Component({
  selector: 'app-loan-form',
  templateUrl: './loan-form.component.html',
  styleUrls: ['./loan-form.component.css']
})
export class LoanFormComponent implements OnInit {

  loanForm: FormGroup;
  submitted: boolean = false;
  loan: Loan = {
    custId : undefined,
    loanAmount: undefined,
    loanStartDate: undefined,
    interestRate: undefined,
    tenure: undefined,
    tradeDate: undefined,
    paymentFrequency: undefined,
    evenPrincipal: undefined,
    maturityDate: undefined
  };
  formDate: Date;
  monthValue: number;

  constructor(private formBuilder: FormBuilder, private loanApiService: LoanApiService, private activatedRouter: ActivatedRoute, private router: Router) { }

  ngOnInit(): void {
    if(localStorage.getItem("isLoggedin")){

    this.loanForm = this.formBuilder.group({
      custId: ["", Validators.required],
      loanAmount: ["", Validators.required],
      interestRate: ["", Validators.required],
      paymentFrequency: ["", Validators.required],
      startDate: ["", Validators.required],
      tenure: ["", Validators.required],
      evenPrincipal: ["true", Validators.required],
    });
  }

  }
  onSubmit(){
    
    if(this.loanForm.invalid){
      console.log(this.loanForm.valid);
      console.log("invalid");
      console.log(this.loanForm.value);
      
      return;
    }

    else{
      
      this.submitted = true;
      this.loan.custId = this.loanForm.value.custId;
      this.loan.loanAmount = this.loanForm.value.loanAmount;
      this.loan.interestRate = this.loanForm.value.interestRate;
      this.loan.loanStartDate = this.loanForm.value.startDate;
      this.loan.paymentFrequency = this.loanForm.value.paymentFrequency;
      this.loan.tenure = this.loanForm.value.tenure;
      this.loan.evenPrincipal = this.loanForm.value.evenPrincipal;
      this.loan.tradeDate = new Date();
      
      this.loanApiService.createLoan(this.loan).subscribe(response =>{
         console.log(this.loanForm.value);
        console.log(response);
        this.loan = <Loan>response;
        this.loanForm.reset;
        alert("Loan Created! :)" );
      this.router.navigate(['homePage', this.activatedRouter.snapshot.params['name']] );
      });

      this.loanForm.reset();
      
    }
      
  }

}
