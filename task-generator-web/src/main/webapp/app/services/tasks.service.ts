import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'webapp/environments/environment';
import { Task } from '../types/task';
import { AccountService } from './account.service';

@Injectable({
  providedIn: 'root'
})
export class TasksService {

  private taskListUrl = environment.apiUrl +  '/api/tasks'

  constructor(private http: HttpClient, private accountService: AccountService) { }

  getTasks(): Observable<Task[]> {
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': this.accountService.getToken()
    })
    return this.http.get<Task[]>(this.taskListUrl, {headers})
  }
}
