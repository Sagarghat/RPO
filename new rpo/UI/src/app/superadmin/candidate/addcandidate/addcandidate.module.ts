import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AddcandidateComponent } from './addcandidate/addcandidate.component';
import { RouterModule } from '@angular/router';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { routes } from './addcandidate.routing';
import { NgxSpinnerModule } from "ngx-spinner";



@NgModule({
  declarations: [AddcandidateComponent],
  imports: [
    CommonModule,
    RouterModule.forChild(routes),
    FormsModule,
   ReactiveFormsModule,
   NgxSpinnerModule,
   
  
   
  ]
})
export class AddcandidateModule { }
