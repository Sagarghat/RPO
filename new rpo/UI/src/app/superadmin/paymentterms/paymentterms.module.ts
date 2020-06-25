import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { PaymenttermsComponent } from './paymentterms/paymentterms.component';
import { RouterModule } from '@angular/router';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import {NgxPaginationModule} from 'ngx-pagination';
import { routes } from './paymentterms.router';


@NgModule({
  declarations: [PaymenttermsComponent],
  imports: [
    CommonModule,
    RouterModule.forChild(routes),
    FormsModule,
     ReactiveFormsModule,
     NgxPaginationModule
  ]
})
export class PaymenttermsModule { }
