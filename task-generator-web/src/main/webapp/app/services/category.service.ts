import { Injectable } from '@angular/core';
import { BehaviorSubject, PartialObserver } from 'rxjs';
import { Category } from '../types/category';

@Injectable({
  providedIn: 'root'
})
export class CategoryService {
  private $categories: BehaviorSubject<Category[]> = new BehaviorSubject([
    {id: 1, name: "Combat", color: "#a32d15"},
    {id: 2, name: "Achievement", color: "#170bbf"},
    {id: 3, name: "Event", color: "#1298b0"}
  ])


  constructor() { }

  subscribe(observer: PartialObserver<Category[]>) {
    this.$categories.subscribe(observer)
    if(observer.next) {
      observer.next(this.$categories.getValue())
    }
  }

  addCategory(name: string, color: string) {
    let id = Math.round(Math.random() * 100000)
    const category: Category = { name, color, id: id }
    this.$categories.next(
      [...this.$categories.getValue(), category]
    )
  }

  edit(category: Category) {
    this.$categories.next(
      [...this.$categories.getValue().filter(c => c.id != category.id), category]
    )
  }

  remove(category: Category) {
    this.$categories.next(
      this.$categories.getValue().filter(c => c.id != category.id)
    )
  }
}
