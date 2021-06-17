import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, of, PartialObserver } from 'rxjs';
import { catchError, map } from 'rxjs/operators';
import { environment } from 'webapp/environments/environment';
import { Category } from '../types/category';
import { ProjectService } from './project.service';

@Injectable({
  providedIn: 'root'
})
export class CategoryService {

  private readonly categoriesUrl = environment.apiUrl + '/api/categories'

  private $categories: BehaviorSubject<Category[]> = new BehaviorSubject([] as Category[])


  constructor(private http: HttpClient, private projectService: ProjectService) {
    this.fetchCategories()
  }

  subscribe(observer: PartialObserver<Category[]>) {
    this.$categories.subscribe(observer)
    if (observer.next) {
      observer.next(this.$categories.getValue())
    }
  }

  addCategory(name: string, color: string) {
    console.log('add')
    return this.projectService.getDefaultProject().subscribe(project => {
      console.log('add' + project.owner)
      const category: Category = { name, color, projectId: project.id}
      return this.http.post<Category>(this.categoriesUrl, category, {
        headers: {
          'Content-Type': 'application/json'
        }
      }).subscribe(category => {
        this.$categories.next(
          [...this.$categories.getValue(), category]
        )
      })
    })
  }

  edit(category: Category) {
    return this.http.put<Category>(this.categoriesUrl, category).subscribe(category => 
      this.$categories.next(
        [...this.$categories.getValue().filter(c => c.id != category.id), category]
      )
    )
  }

  remove(category: Category) {
    return this.http.delete<Category>(this.categoriesUrl + '?categoryId=' + category.id).subscribe(x =>
      this.$categories.next(
        this.$categories.getValue().filter(c => c.id != category.id)
      )
    )
  }

  fetchCategories() {
    this.projectService.getDefaultProject().subscribe(project => {
      this.http.get<[Category]>(this.categoriesUrl + '?projectId=' + project.id)
        .pipe(
          catchError((err: HttpErrorResponse) => {
            console.error(err)
            return of([])
          })
        ).subscribe({
          next: (categories) => {
            this.$categories.next(categories)
          }
        })
    })
  }
}
