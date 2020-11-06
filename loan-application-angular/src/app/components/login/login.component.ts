import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms'
import { Router } from '@angular/router';
import {LoanApiService} from "../../services/loan-api.service";
import {BankEmployee} from '../../models/BankEmployee';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  loginForm: FormGroup;
  submitted: boolean = false;
  bankEmployee: BankEmployee = {
    name: undefined,
    password: undefined
  };
  constructor(private formBuilder: FormBuilder, private loanApiService: LoanApiService, private router:Router) { }

  ngOnInit(): void {
    this.loginForm = this.formBuilder.group({
      Name: ["", Validators.required],
      Password: ["", [Validators.required, Validators.minLength(5)]],
    })

  }

  onSubmit(){
    this.submitted = true;
    if(this.loginForm.invalid){
      return;
    }

    else{
      //  this.bankEmployee.name = this.loginForm.value.Name;
      //  this.bankEmployee.password =  this.loginForm.value.Password;
      //  console.log(this.bankEmployee);
      this.loanApiService.authenticate(this.loginForm.value.Name, this.loginForm.value.Password).subscribe(
        response => {
          if(response == null)
          {
            alert("Invalid Credentials. :(")
          }
          if(response != null){
          //alert("Success signup! :)");
          console.log(response);
          this.loanApiService.currentUser = this.loginForm.value.Name;
          this.loanApiService.isLoggedIn = true;
          localStorage.setItem('isLoggedin', 'true');
          if(this.loanApiService.isLoggedIn)
          this.router.navigate(['Loan-form']);
         
          }
         });
    }
 }

}
