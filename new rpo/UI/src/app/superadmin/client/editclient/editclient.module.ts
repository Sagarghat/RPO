import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { EditclientComponent } from './editclient/editclient.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { routes } from './editclient.router';
import {NgxPaginationModule} from 'ngx-pagination';


@NgModule({
  declarations: [EditclientComponent],
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    RouterModule.forChild(routes),
    NgxPaginationModule
  ]
})
export class EditclientModule { }
