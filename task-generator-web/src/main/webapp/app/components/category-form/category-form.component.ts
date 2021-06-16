import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { CategoryService } from 'webapp/app/services/category.service';
import { Category } from 'webapp/app/types/category';

@Component({
  selector: 'app-category-form',
  templateUrl: './category-form.component.html',
  styleUrls: ['./category-form.component.scss']
})
export class CategoryFormComponent implements OnInit {
  
  @Output() finished = new EventEmitter<null>();
  formTitle = 'Add Category'
  category: Category | null = null
  error: string | null = null

  constructor(private categoryService: CategoryService) { }

  categoryForm = new FormGroup({
    name: new FormControl('', 
      [Validators.required, Validators.minLength(1), Validators.maxLength(16)]),
    color: new FormControl('#000000',
      [Validators.required])
  })

  ngOnInit(): void {
  }

  setCategory(category: Category|null) {
    if(category === null) {
      this.formTitle = 'Add Category'
    } else {
      this.formTitle = 'Edit Category'
    }
    this.category = category
    this.categoryForm.setValue({
      name: category?.name ?? '',
      color: category?.color ?? '#000000'
    })
  }

  submit() {
    if(this.categoryForm.invalid) {
      this.error = 'Name must be 1 - 16 characters long'
    } else {
      this.error = null
      console.log(this.categoryForm.value)
      if(this.category === null) {
        this.categoryService.addCategory(this.categoryForm.value.name, this.categoryForm.value.color)
      } else {
        this.category.name = this.categoryForm.value.name
        this.category.color = this.categoryForm.value.color
        this.categoryService.edit(this.category)
      }
      this.finished.emit()
    }
  }
}
