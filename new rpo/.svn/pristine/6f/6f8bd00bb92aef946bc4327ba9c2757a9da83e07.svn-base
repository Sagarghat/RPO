import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AdduserComponent } from './adduser/adduser.component';
import { RouterModule } from '@angular/router';
import { routes } from './adduser.routing';
import { FormsModule, ReactiveFormsModule } from '@angular/forms'
import {NgxPaginationModule} from 'ngx-pagination';

@NgModule({
  declarations: [
    AdduserComponent,
  
  ],
  imports: [
    CommonModule,
    RouterModule.forChild(routes),
    FormsModule,
    NgxPaginationModule,
    ReactiveFormsModule,
  ]
})
export class AdduserModule {

  constructor() {
    console.log("AdduserModule");
  }
}
