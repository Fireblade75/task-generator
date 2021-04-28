import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AccountService } from '../services/account.service';

@Component({
  selector: 'app-register-page',
  templateUrl: './register-page.component.html',
  styleUrls: ['./register-page.component.scss']
})
export class RegisterPageComponent implements OnInit {

  constructor(private accountService: AccountService, private router: Router) { }

  ngOnInit(): void {
  }

  errorMessage = '';

  registerForm = new FormGroup({
    email: new FormControl('', 
      [Validators.required, Validators.email, Validators.maxLength(64)]),
    password: new FormControl('',
      [Validators.required, Validators.minLength(6), Validators.maxLength(32)]),
    tos: new FormControl(false, Validators.requiredTrue)
  });

  register() {
    if(this.registerForm.valid) {
      this.accountService.register(this.registerForm.value)
        .subscribe(email => {
          if(email != '') {
            this.router.navigate(['/login', { msg: 'registered' }])
          } else {
            this.errorMessage = "Registratie request is mislukt";
          }
        })
      
    } else if(this.registerForm.get('email')?.invalid) {
      this.errorMessage = "Ongeldig e-mailadres";
    } else if(this.registerForm.get('password')?.invalid) {
      this.errorMessage = "Wachtwoord moet tussen 6 en 32 tekens bevatten";
    } else if(this.registerForm.get('tos')?.invalid) {
      this.errorMessage = "Je moet akkoord gaan met de algemene voorwaarden";
    }
  }
}
