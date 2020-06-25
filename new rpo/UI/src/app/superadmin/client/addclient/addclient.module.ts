import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AddclientComponent } from './addclient/addclient.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { routes } from './addclient.router';
import {NgxPaginationModule} from 'ngx-pagination';

@NgModule({
  declarations: [
    AddclientComponent,
   
  ],
  imports: [
    CommonModule,
    RouterModule.forChild(routes),
    FormsModule,
    ReactiveFormsModule,
    NgxPaginationModule
  ]
  
})
export class AddclientModule { }
