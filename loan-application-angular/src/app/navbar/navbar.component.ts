import { Component, OnInit } from '@angular/core';
import{LoanApiService} from '../services/loan-api.service';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {

  constructor(loanApiService: LoanApiService) { }

  ngOnInit(): void {

  }

}
