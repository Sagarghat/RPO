import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { IntervComponent } from './interv/interv.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { routes } from './interv.routing';
import {NgxPaginationModule} from 'ngx-pagination';


@NgModule({
  declarations: [IntervComponent],
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    NgxPaginationModule,
    RouterModule.forChild(routes),
  ]
})
export class IntervModule { }
