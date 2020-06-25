import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AddcanresumeComponent } from './addcanresume/addcanresume.component';
import { RouterModule } from '@angular/router';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { routes } from './addcanresume.routing';






@NgModule({
  declarations: [AddcanresumeComponent],
  imports: [
   
    CommonModule,
    RouterModule.forChild(routes),
    FormsModule,
   ReactiveFormsModule
  ]
})
export class AddcanresumeModule { }
