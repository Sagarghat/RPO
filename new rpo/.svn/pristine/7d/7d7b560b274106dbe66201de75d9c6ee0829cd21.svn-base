import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { UserComponent } from './user/user.component';
import { RouterModule } from '@angular/router';
import { routes } from './user.routing';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import {NgxPaginationModule} from 'ngx-pagination';

@NgModule({
  declarations: [UserComponent],
  imports: [
    CommonModule,
    FormsModule,
    RouterModule.forChild(routes),
    NgxPaginationModule,
    ReactiveFormsModule

  ]
})
export class UserModule {
  constructor(){
    console.log("UserModule");
  }
 }
