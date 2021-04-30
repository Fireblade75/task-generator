import { Component, OnInit } from '@angular/core';
import { NavigationStart, Router } from '@angular/router';
import { filter } from 'rxjs/operators';
import { AccountService } from 'webapp/app/services/account.service';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.scss']
})
export class NavbarComponent implements OnInit {

  loggedIn: boolean;

  constructor(private accountService: AccountService, private router: Router) { 
    this.loggedIn = accountService.isLoggedIn()
    router.events.pipe(filter(event => event instanceof NavigationStart))
      .subscribe(_ => {
        this.loggedIn = accountService.isLoggedIn()
      }) 
  }

  ngOnInit(): void {
  }

  logout(e: Event) {
    e.preventDefault();
    this.accountService.logout()
  }
}
