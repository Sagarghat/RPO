import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ModeofinterviewComponent } from './modeofinterview/modeofinterview.component';
import { RouterModule } from '@angular/router';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import {NgxPaginationModule} from 'ngx-pagination';
import { routes } from './modeofinterview.router';


@NgModule({
  declarations: [ModeofinterviewComponent],
  imports: [
    CommonModule,
    RouterModule.forChild(routes),
    FormsModule, 
    ReactiveFormsModule,
    NgxPaginationModule
  ]
})
export class ModeofinterviewModule { }
