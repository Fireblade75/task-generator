import { Component, OnInit } from '@angular/core';
import { AccountService } from 'webapp/app/services/account.service';

@Component({
  selector: 'app-test-page',
  templateUrl: './test-page.component.html',
  styleUrls: ['./test-page.component.scss']
})
export class TestPageComponent implements OnInit {

  public content: string =  'hoi'

  constructor(private accountService: AccountService) { }

  ngOnInit(): void {
    
  }

}
