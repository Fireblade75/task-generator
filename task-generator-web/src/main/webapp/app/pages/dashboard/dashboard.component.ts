import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { CategoryService } from 'webapp/app/services/category.service';
import { Category } from 'webapp/app/types/category';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss']
})
export class DashboardComponent implements OnInit {

  categories: Category[] = [] as Category[]

  taskForm = new FormGroup({
    name: new FormControl('', 
      [Validators.required, Validators.minLength(1), Validators.maxLength(16)]),
    description: new FormControl('',
      [Validators.required, Validators.minLength(1), Validators.maxLength(256)])
  })


  constructor(private categoryService: CategoryService) { } 

  ngOnInit(): void {
    this.categoryService.subscribe({
      next: (categoryList) => this.categories = categoryList 
    })
  }

}
