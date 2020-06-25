import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { JobopeningComponent } from './jobopening/jobopening.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { RouterModule } from '@angular/router';
import { PaginationModule } from 'ngx-bootstrap/pagination';
import { routes } from './jobopening.routing';
import {NgxPaginationModule} from 'ngx-pagination';
import { PdfViewerModule } from 'ng2-pdf-viewer';
@NgModule({
  declarations: [JobopeningComponent],
  imports: [
    CommonModule,
    FormsModule,
    HttpClientModule,
    ReactiveFormsModule,
    RouterModule.forChild(routes),
    PaginationModule.forRoot(),
    NgxPaginationModule,
    PdfViewerModule
  ]
})
export class JobopeningModule { }