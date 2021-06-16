import { Component, OnInit } from '@angular/core';
import { MatIconRegistry } from '@angular/material/icon';
import { } from '@angular/material/icon/'

@Component({
  selector: 'app-task-list',
  templateUrl: './task-list.component.html',
  styleUrls: ['./task-list.component.scss']
})
export class TaskListComponent implements OnInit {

  tasks = [
    {
      "description": "Kill the evil green dragon that burned the village",
      "name": "Slay the dragon",
      "done": false
    },
    {
      "description": "The princess is trapped in the dragon's lair",
      "name": "Save the princess",
      "done": true
    },
    {
      "description": "Recreate the blade of legends",
      "name": "Reforge Excalibur",
      "done": false
    }
  ]

  constructor(iconRegistry: MatIconRegistry) {

  }

  ngOnInit(): void {
  }

}
