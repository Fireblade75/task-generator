import { Component, Input, OnInit } from '@angular/core';
import { Task } from 'webapp/app/types/task';

@Component({
  selector: 'app-task-row',
  templateUrl: './task-row.component.html',
  styleUrls: ['./task-row.component.scss']
})
export class TaskRowComponent implements OnInit {

  @Input() task!: Task;
  doneImg: string = ''

  constructor() { }

  ngOnInit(): void {
    this.doneImg = 'assets/img/' + (this.task.done ? 'uncheck.svg' : 'check.svg')
  }

}
