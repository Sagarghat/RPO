import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ServicelistComponent } from './servicelist/servicelist.component';
import { RouterModule } from '@angular/router';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import {NgxPaginationModule} from 'ngx-pagination';
import { routes } from './servicelist.router';

@NgModule({
  declarations: [ServicelistComponent],
  imports: [
    CommonModule,
    RouterModule.forChild(routes),
    FormsModule,
    ReactiveFormsModule,
    NgxPaginationModule
  ]
})
export class ServicelistModule { }
