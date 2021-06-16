import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { CategoryService } from 'webapp/app/services/category.service';
import { Category } from 'webapp/app/types/category';

@Component({
  selector: 'app-category-list',
  templateUrl: './category-list.component.html',
  styleUrls: ['./category-list.component.scss']
})
export class CategoryListComponent implements OnInit {
  
  categories: Category[] = [];

  @Output() addCategory = new EventEmitter<null>();
  @Output() editCategory = new EventEmitter<Category>()

  constructor(private categoryService: CategoryService) { }

  ngOnInit(): void {
    this.categoryService.subscribe({
      next: categories => this.categories = categories
    })
  }

  deleteCategory(category: Category) {
    this.categoryService.remove(category)
  }
}
