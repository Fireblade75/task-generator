import { HttpClient, HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { catchError, map } from 'rxjs/operators';
import { ApiError, isApiError, RequestResponse } from '../types/common';
import * as moment from 'moment';
import { CanActivate, Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class AccountService implements CanActivate  {
  private loginUrl = 'api/accounts/login'
  private registerUrl = 'api/accounts/register'

  constructor(private http: HttpClient, private router: Router) { }

  login(credentials: LoginRequest): Observable<RequestResponse> {
    return this.http.post<CredentialsResponse>(this.loginUrl, credentials, {observe: 'response'})
      .pipe(
        map(resp => {
          if (resp.headers.has('Authorization') && resp.body?.expiresIn != null) {
            const authToken = resp.headers.get('Authorization') as string
            const expiresAt = moment().add(resp.body.expiresIn, 'second');

            localStorage.setItem('auth_token', authToken);
            localStorage.setItem("expires_at", JSON.stringify(expiresAt.valueOf()));
            return { ok: true, msg: '' }
          } else {
            return { ok: false, msg: 'Authorization token of expire time ontbreekt' }
          }
        }),
        catchError((err: HttpErrorResponse) => {
          if(err.error instanceof Error) {
            return of({ ok: false, msg: 'Er is een fout opgetreden: ' + err.error.message })
          } if(err.status == 401) {
            return of({ ok: false, msg: 'E-mail of wachtwoord onjuist' })
          } if(isApiError(err.error)) {
            return of({ ok: false, msg: err.error.error })
          } else {
            return of({ ok: false, msg: '' + err.error })
          }
        })
      )
  }

  logout() {
    localStorage.removeItem("auth_token");
    localStorage.removeItem("expires_at");
    this.router.navigate(['login']);
  }

  getExpiration() {
    const expiration = localStorage.getItem("expires_at");
    const expiresAt = expiration ? JSON.parse(expiration) : 0;
    return moment(expiresAt);
  }

  isLoggedIn() {
    return moment().isBefore(this.getExpiration());
  }

  register(credentials: RegisterRequest): Observable<RequestResponse> {
    return this.http.post<ApiError>(this.registerUrl, credentials)
      .pipe(
        map(resp => {
          return { ok: true, msg: '' }
        }),
        catchError((err: HttpErrorResponse, resp) => {
          if(err.error instanceof Error) {
            return of({ ok: false, msg: 'Er is een fout opgetreden: ' + err.error.message })
          } if(err.status == 409) {
            return of({ ok: false, msg: 'E-mail is al geregistreerd' })
          } if(isApiError(err.error)) {
            return of({ ok: false, msg: err.error.error })
          } else {
            return of({ ok: false, msg: '' + err.error })
          }
        })
      )
  }

  getToken(): string {
    let token = localStorage.getItem("auth_token")
    if(token == null) {
      throw new Error('Auth token unavailable')
    }
    return token;
  }

  canActivate(): boolean {
    if(!this.isLoggedIn()) {
      this.router.navigate(['login']);
      return false;
    }
    return true;
  }
}

export interface RegisterRequest {
  email: string,
  password: string,
  tos: boolean
}

export interface LoginRequest {
  email: string,
  password: string
}

interface CredentialsResponse {
  email: string,
  expiresIn: number
}
