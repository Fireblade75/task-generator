import { HttpClient, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { catchError, map } from 'rxjs/operators';
import { ApiError, isApiError, RequestResponse } from './common';
import * as moment from 'moment';

@Injectable({
  providedIn: 'root'
})
export class AccountService {

  private loginUrl = 'api/accounts/login'
  private registerUrl = 'api/accounts/register'

  constructor(private http: HttpClient) { }

  login(credentials: LoginRequest): Observable<RequestResponse> {
    return this.http.post<HttpResponse<CredentialsResponse | ApiError>>(this.loginUrl, credentials)
      .pipe(
        map(resp => {
          if (resp.body && !isApiError(resp.body)) {
            resp.headers.get('Authorization')

            return { ok: true, msg: '' }
          } else {
            return { ok: false, msg: 'E-mail of wachtwoord onjuist' }
          }
        }),
        catchError(err => of({ ok: false, msg: 'Er is een fout opgetreden' }))
      )
  }

  logout() {
    localStorage.removeItem("id_token");
    localStorage.removeItem("expires_at");
  }

  register(credentials: RegisterRequest): Observable<RequestResponse> {
    return this.http.post<HttpResponse<ApiError>>(this.registerUrl, credentials)
      .pipe(
        map(resp => {
          if (resp.status == 201 && !isApiError(resp)) {
            return { ok: true, msg: '' }
          } else {
            return { ok: false, msg: resp.body ? resp.body.error 
              : 'Er is een fout opgetreden, http status ' + resp.status }
          }
        }),
        catchError(err => of({ ok: false, msg: 'Er is een fout opgetreden' }))
      )
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
  email: string
}
