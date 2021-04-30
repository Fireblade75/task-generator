import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { AccountService } from '../../services/account.service';

@Component({
  selector: 'app-login-page',
  templateUrl: './login-page.component.html',
  styleUrls: ['./login-page.component.scss']
})
export class LoginPageComponent implements OnInit {

  message = {
    text: '',
    type: ''
  }

  loginForm = new FormGroup({
    email: new FormControl('', 
      [Validators.required, Validators.email]),
    password: new FormControl('',
      [Validators.required])
  });

  constructor(private accountService: AccountService, private route: ActivatedRoute, private router: Router) { }

  ngOnInit(): void {
    if(this.accountService.isLoggedIn()) {
      this.router.navigate(['/tasklist'])
    }

    this.route.paramMap.subscribe(params => {
      const msg = params.get('msg')
      if (msg) {
        switch (msg) {
          case 'registered':
            this.message = { text: 'Account succesvol geregistreerd, je kunt nu inloggen', type: 'text' }
            break;
          default:
            this.message = { text: msg, type: 'error' }
        }
      }
    })
  }

  login() {
    if(this.loginForm.invalid) {
      this.message = {
        text: 'Vul alle velden correct in',
        type: 'error'
      }
    } else {
      this.accountService.login(this.loginForm.value)
        .subscribe(response => {
          if(response.ok) {
            this.router.navigate(['/tasklist'])
          } else {
            this.message = { text: response.msg, type: 'error' }
          }
        })
    }
  }
}
