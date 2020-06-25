import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { EditinterviewComponent } from './editinterview/editinterview.component';
import { RouterModule } from '@angular/router';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { routes } from './editinterview.router';
import {NgxPaginationModule} from 'ngx-pagination';

@NgModule({
  declarations: [EditinterviewComponent],
  imports: [
    CommonModule,
    RouterModule.forChild(routes),
    FormsModule,
    NgxPaginationModule,
    ReactiveFormsModule
  ]
})
export class EditinterviewModule { 
  
  
}
