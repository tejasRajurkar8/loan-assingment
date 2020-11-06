import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { LoanFormComponent } from './components/loan-form/loan-form.component';
import { LoginComponent } from './components/login/login.component';
import { ViewLoanComponent } from './components/view-loan/view-loan.component';

const routes: Routes = [
  {path: '', component: LoginComponent},
  {path: 'Loan-form', component: LoanFormComponent},
  {path: 'Loan', component: ViewLoanComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
