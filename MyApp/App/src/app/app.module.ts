import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppComponent } from './app.component';
import { LoginComponent } from './login/login.component';
import { TasksComponent } from './tasks/tasks.component';
import { NewTasksComponent } from './new-tasks/new-tasks.component';
import { RegistrationComponent } from './registration/registration.component';
import {RouterModule, Routes} from '@angular/router';
import {FormsModule} from '@angular/forms';
import {HttpClientModule} from '@angular/common/http';
import {AuthentificationService} from './service/authentification.service';

const appRoutes:Routes=[
  {path:"login",component:LoginComponent},
  {path:"tasks",component:TasksComponent},
  {path:"new-tasks",component:NewTasksComponent},
  {path:"registration",component:RegistrationComponent},
  {path:"",redirectTo:"login", pathMatch:"full"}
  ];

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    TasksComponent,
    NewTasksComponent,
    RegistrationComponent
  ],
  imports: [
    BrowserModule,
    RouterModule.forRoot(appRoutes),
    FormsModule,
    HttpClientModule
  ],
  providers: [AuthentificationService],
  bootstrap: [AppComponent]
})
export class AppModule { }
