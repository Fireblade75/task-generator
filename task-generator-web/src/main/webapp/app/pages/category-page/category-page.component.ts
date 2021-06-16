import { Component, OnInit, ViewChild } from '@angular/core';
import { CategoryFormComponent } from 'webapp/app/components/category-form/category-form.component';
import { Category } from 'webapp/app/types/category';

@Component({
  selector: 'app-category-page',
  templateUrl: './category-page.component.html',
  styleUrls: ['./category-page.component.scss']
})
export class CategoryPageComponent implements OnInit {

  editVisibility: 'visible' | 'hidden' = 'hidden'
  
  @ViewChild(CategoryFormComponent)
  formComponent!: CategoryFormComponent;

  constructor() { }

  ngOnInit(): void {
  }

  addCategory() {
    this.formComponent.setCategory(null)
    this.editVisibility = 'visible'
  }

  editCategory(category: Category) {
    this.formComponent.setCategory(category)
    this.editVisibility = 'visible'
  }

  finishedEdit() {
    this.editVisibility = 'hidden'
  }

}
