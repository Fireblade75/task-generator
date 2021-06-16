import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { Category } from 'webapp/app/types/category';

@Component({
  selector: 'app-category-row',
  templateUrl: './category-row.component.html',
  styleUrls: ['./category-row.component.scss']
})
export class CategoryRowComponent implements OnInit {

  @Input() category!: Category;
  @Output() editCategory = new EventEmitter<Category>()
  @Output() deleteCategory = new EventEmitter<Category>()

  constructor() { }

  ngOnInit(): void {
    
  }

}
