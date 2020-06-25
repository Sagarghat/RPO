import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { EditcontactComponent } from './editcontact/editcontact.component';
import { RouterModule } from '@angular/router';
import { routes } from './editcontact.router';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { PaginationModule } from 'ngx-bootstrap/pagination';
import { NgxPaginationModule } from 'ngx-pagination';

@NgModule({
  declarations: [EditcontactComponent],
  imports: [
    CommonModule,
    RouterModule,
    ReactiveFormsModule,
    FormsModule,
    PaginationModule,
    RouterModule.forChild(routes),
    NgxPaginationModule
  ]
})
export class EditcontactModule { }
