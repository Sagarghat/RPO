import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CertificatesComponent } from './certificates/certificates.component';
import { RouterModule } from '@angular/router';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import {NgxPaginationModule} from 'ngx-pagination';
import { routes } from './certificates.router';


@NgModule({
  declarations: [CertificatesComponent],
  imports: [
    CommonModule,
    RouterModule.forChild(routes),
    FormsModule,
    ReactiveFormsModule,
    NgxPaginationModule
  ]
})
export class CertificatesModule { }
