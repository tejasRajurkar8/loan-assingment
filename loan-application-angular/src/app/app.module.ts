import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginComponent } from '../app/components/login/login.component';
import { LoanFormComponent } from './components/loan-form/loan-form.component';

import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { ViewLoanComponent } from './components/view-loan/view-loan.component';
import { NavbarComponent } from './navbar/navbar.component';
import { HttpInterceptorBasicAuthService } from './services/http/http-interceptor-basic-auth.service';


@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    ViewLoanComponent,
    LoanFormComponent,
    NavbarComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    ReactiveFormsModule,
    HttpClientModule,
    FormsModule
  ],
  providers: [
    {provide: HTTP_INTERCEPTORS, useClass: HttpInterceptorBasicAuthService , multi: true}],
  bootstrap: [AppComponent]
})
export class AppModule { }
