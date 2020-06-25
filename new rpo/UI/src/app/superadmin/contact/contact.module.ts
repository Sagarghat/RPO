import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ContactComponent } from './contact/contact.component';
import { RouterModule } from '@angular/router';
import { routes } from './contact.routing';

import { HttpClientModule } from '@angular/common/http';
import {NgxPaginationModule} from 'ngx-pagination';

import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import { PdfViewerModule } from 'ng2-pdf-viewer';





@NgModule({
  declarations: [ContactComponent],
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule,
    RouterModule.forChild(routes),
    NgxPaginationModule,
    ReactiveFormsModule,
    PdfViewerModule,
    NgxPaginationModule,

  ]
})
export class ContactModule { }