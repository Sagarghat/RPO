import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CandidatedetailsComponent } from './candidatedetails/candidatedetails.component';
import { routes } from './candidatedetails.routing';
import { RouterModule } from '@angular/router';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';

@NgModule({
  declarations: [CandidatedetailsComponent],
  imports: [
    CommonModule,
    RouterModule.forChild(routes),
    FormsModule,
    ReactiveFormsModule,
    NgbModule
  ]
})
export class CandidatedetailsModule { }
