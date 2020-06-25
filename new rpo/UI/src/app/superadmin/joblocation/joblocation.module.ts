import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { JoblocationComponent } from './joblocation/joblocation.component';
import { RouterModule } from '@angular/router';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import {NgxPaginationModule} from 'ngx-pagination';
import { routes } from './joblocation.router';


@NgModule({
  declarations: [JoblocationComponent],
  imports: [
    CommonModule,
    RouterModule.forChild(routes),
    FormsModule,
    ReactiveFormsModule,
    NgxPaginationModule
  ]
})
export class JoblocationModule { }
