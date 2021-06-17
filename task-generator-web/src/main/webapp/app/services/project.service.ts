import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { catchError, map } from 'rxjs/operators';
import { environment } from 'webapp/environments/environment';
import { Project } from '../types/project';

@Injectable({
  providedIn: 'root'
})
export class ProjectService {
  private readonly projectsUrl = environment.apiUrl + '/api/projects'

  constructor(private http: HttpClient) { }

  getProjects() {
    return this.http.get<[Project]>(this.projectsUrl)
      .pipe(
        catchError((err: HttpErrorResponse) => {
          console.error(err)
          return of([])
        })
      )
  }

  getDefaultProject(): Observable<Project> {
    return this.http.get<[Project]>(this.projectsUrl)
      .pipe(
        map(projects => {
          const defaultList = projects.filter(p => p.projectName === 'defaultProject')
          return defaultList.length > 0 ? defaultList[0] : null as any
        }),
        catchError((err: HttpErrorResponse) => {
          console.error(err)
          return of(null as any)
        })
      )
  }
}
