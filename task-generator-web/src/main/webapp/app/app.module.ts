import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { MatIconModule } from '@angular/material/icon';
import { MatListModule } from '@angular/material/list'

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginPageComponent } from './pages/login-page/login-page.component';
import { RegisterPageComponent } from './pages/register-page/register-page.component';
import { PageNotFoundComponent } from './pages/page-not-found/page-not-found.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { TaskListPageComponent } from './pages/task-list-page/task-list-page.component';
import { NavbarComponent } from './components/navbar/navbar.component';
import { DashboardComponent } from './pages/dashboard/dashboard.component';
import { ManagePageComponent } from './pages/manage-page/manage-page.component';
import { ProjectsPageComponent } from './pages/projects-page/projects-page.component';
import { TaskRowComponent } from './components/task-row/task-row.component';
import { CategoryFormComponent } from './components/category-form/category-form.component';
import { CategoryListComponent } from './components/category-list/category-list.component';
import { CategoryPageComponent } from './pages/category-page/category-page.component';
import { CategoryRowComponent } from './components/category-row/category-row.component';
import { JwtInterceptor } from './services/jwt.interceptor';

@NgModule({
  declarations: [
    AppComponent,
    LoginPageComponent,
    RegisterPageComponent,
    PageNotFoundComponent,
    TaskListPageComponent,
    NavbarComponent,
    DashboardComponent,
    ManagePageComponent,
    ProjectsPageComponent,
    TaskRowComponent,
    CategoryFormComponent,
    CategoryListComponent,
    CategoryPageComponent,
    CategoryRowComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    ReactiveFormsModule,
    HttpClientModule,
    MatIconModule,
    MatListModule,
    FormsModule 
  ],
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: JwtInterceptor,
      multi: true
    }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
