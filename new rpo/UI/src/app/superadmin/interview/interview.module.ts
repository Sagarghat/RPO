import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { routes } from './interview.router';
import { InterviewComponent } from './interview/interview.component';
import {NgxPaginationModule} from 'ngx-pagination';
import { PdfViewerModule } from 'ng2-pdf-viewer';
@NgModule({
  declarations: [InterviewComponent],
  imports: [
    CommonModule,
    FormsModule,
    RouterModule.forChild(routes),
    NgxPaginationModule,
    PdfViewerModule,
    ReactiveFormsModule,
  ]
})
export class InterviewModule { }
