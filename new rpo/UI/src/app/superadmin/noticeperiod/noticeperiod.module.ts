import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NoticeperiodComponent } from './noticeperiod/noticeperiod.component';
import { RouterModule } from '@angular/router';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import {NgxPaginationModule} from 'ngx-pagination';
import { routes } from './noticeperiod.router';

@NgModule({
  declarations: [NoticeperiodComponent],
  imports: [
    CommonModule,
    RouterModule.forChild(routes),
    FormsModule, 
    ReactiveFormsModule,
    NgxPaginationModule
  ]
})
export class NoticeperiodModule { }
