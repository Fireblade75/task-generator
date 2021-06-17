import { HttpEvent, HttpHandler, HttpInterceptor, HttpRequest, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

@Injectable()
export class JwtInterceptor implements HttpInterceptor {
    public static readonly AUTH_TOKEN_KEY = 'auth_token'

    intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        const token = localStorage.getItem(JwtInterceptor.AUTH_TOKEN_KEY)
        if (token !== null) {
            req = req.clone({
                setHeaders: {
                    Authorization: token
                }
            });
        }

        return next.handle(req).pipe(
            map(event => {
                if (event instanceof HttpResponse && event.headers.has('Authorization')) {
                    const token = req.headers.get('Authorization')?.split('Bearer ')[1]
                    if (typeof token === "string") {
                        localStorage.setItem(JwtInterceptor.AUTH_TOKEN_KEY, token)
                    }
                }
                return event
            })
        )
    }
}
