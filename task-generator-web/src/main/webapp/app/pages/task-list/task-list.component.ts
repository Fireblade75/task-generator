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
      "done": false,
      "categories": [{
        "color": "#c46a41",
        "id": 1,
        "name": "Combat",
        "projectId": 1
      }],
      "projectId": 1
    },
    {
      "description": "The princess is trapped in the dragon's lair",
      "name": "Save the princess",
      "done": true,
      "categories": [{
        "color": "#c46a41",
        "id": 1,
        "name": "Combat",
        "projectId": 1
      },{
        "color": "#72b8db",
        "id": 2,
        "name": "Quest",
        "projectId": 1
      }],
      "projectId": 1
    },
    {
      "description": "Recreate the blade of legends",
      "name": "Reforge Excalibur",
      "done": false,
      "categories": [{
        "color": "#72b8db",
        "id": 2,
        "name": "Quest",
        "projectId": 1
      }],
      "projectId": 1
    }
  ]

  constructor(iconRegistry: MatIconRegistry) {

  }

  ngOnInit(): void {
  }

}
