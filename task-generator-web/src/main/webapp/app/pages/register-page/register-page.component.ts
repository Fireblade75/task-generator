import { Component, OnInit } from '@angular/core';
import { AbstractControl, FormControl, FormGroup, ValidationErrors, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { RequestResponse } from 'webapp/app/types/common';
import { AccountService } from '../../services/account.service';

@Component({
  selector: 'app-register-page',
  templateUrl: './register-page.component.html',
  styleUrls: ['./register-page.component.scss']
})
export class RegisterPageComponent implements OnInit {

  constructor(private accountService: AccountService, private router: Router) { }

  ngOnInit(): void {
  }

  errorMessage = ''
  takenEmails = new Set<string>();

  emailValidator = (control: AbstractControl): ValidationErrors|null => {
    return this.takenEmails.has(control.value) ? { emailTaken: true } : null;
  }


  registerForm = new FormGroup({
    email: new FormControl('', [Validators.required, Validators.email,
    Validators.maxLength(64), this.emailValidator]),
    password: new FormControl('',
      [Validators.required, Validators.minLength(6), Validators.maxLength(32)]),
    tos: new FormControl(false, Validators.requiredTrue)
  });

  register() {
    if (this.registerForm.valid) {
      this.accountService.register(this.registerForm.value)
        .subscribe(this.handleRegistration.bind(this))

    } else if (this.registerForm.controls.email.invalid) {
      if (this.takenEmails.has(this.registerForm.value.email)) {
        this.errorMessage = "E-mail is al geregistreerd"
      } else {
        this.errorMessage = "Ongeldig e-mailadres";
      }
    } else if (this.registerForm.get('password')?.invalid) {
      this.errorMessage = "Wachtwoord moet tussen 6 en 32 tekens bevatten";
    } else if (this.registerForm.get('tos')?.invalid) {
      this.errorMessage = "Je moet akkoord gaan met de algemene voorwaarden";
    }
  }

  handleRegistration(response: RequestResponse) {
    if (response.ok) {
      this.router.navigate(['/login', { msg: 'registered' }])
    } else {
      if (response.msg === 'E-mail is al geregistreerd') {
        this.takenEmails.add(this.registerForm.value.email)
      }
      this.errorMessage = response.msg;
    }
  }
}
