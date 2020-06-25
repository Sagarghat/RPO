import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ContactnameclickComponent } from './contactnameclick/contactnameclick.component';
import { RouterModule } from '@angular/router';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { PaginationModule } from 'ngx-bootstrap/pagination';
import { routes } from './contactnameclick.routing';
import { NgxPaginationModule } from 'ngx-pagination';




@NgModule({
  declarations: [ContactnameclickComponent],
  imports: [
    CommonModule,
    ReactiveFormsModule,
    FormsModule,
    HttpClientModule,
    NgxPaginationModule,
    RouterModule.forChild(routes),
    PaginationModule.forRoot()
  ]
})
export class ContactnameclickModule { }
