import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Routes, RouterModule } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

const routes: Routes = [

  // { path: '',  redirectTo: '/login', pathMatch: 'full' },
  // { path: 'login',
  //   children: [
  //     {path:'',component:LoginComponent},
  //     {path:'reg/activate/:id',component:LoginComponent}
  //   ]
  // },


  {
    path: '',
    children: [
      { path: '', component: LoginComponent },
      { path: 'reg/activate/:id', component: LoginComponent }
    ]

  }



];

@NgModule({
  declarations: [LoginComponent],
  imports: [
    RouterModule.forChild(routes),
    FormsModule,
    ReactiveFormsModule
  ]
})
export class LoginModule {
  constructor() {
    console.log('LoginModule');
  }
}
