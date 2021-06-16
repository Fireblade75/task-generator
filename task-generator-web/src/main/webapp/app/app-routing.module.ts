import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CategoryPageComponent } from './pages/category-page/category-page.component';
import { DashboardComponent } from './pages/dashboard/dashboard.component';
import { LoginPageComponent } from './pages/login-page/login-page.component';
import { ManagePageComponent } from './pages/manage-page/manage-page.component';
import { PageNotFoundComponent } from './pages/page-not-found/page-not-found.component';
import { ProjectsPageComponent } from './pages/projects-page/projects-page.component';
import { RegisterPageComponent } from './pages/register-page/register-page.component';
import { TaskListComponent } from './pages/task-list/task-list.component';
import { TestPageComponent } from './pages/test-page/test-page.component';
import { AccountService as LoginGuard } from './services/account.service';

const routes: Routes = [
  { path: 'login', component: LoginPageComponent },
  { path: 'register', component: RegisterPageComponent },
  { path: 'tasklist', component: TaskListComponent /*, canActivate: [LoginGuard]*/ },
  { path: 'dashboard', component: DashboardComponent },
  { path: 'projects', component: ProjectsPageComponent },
  { path: 'manage', component: ManagePageComponent },
  { path: 'manage/categories', component: CategoryPageComponent },
  { path: 'test', component: TestPageComponent },
  { path: '',   redirectTo: '/login', pathMatch: 'full' },
  { path: '**', component: PageNotFoundComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
