import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ClientComponent } from './client/client.component';
import { RouterModule } from '@angular/router';
import { routes } from './client.routing';
import { HttpClient } from 'selenium-webdriver/http';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import {NgxPaginationModule} from 'ngx-pagination';
import { ReactiveFormsModule } from '@angular/forms';
import { PdfViewerModule } from 'ng2-pdf-viewer';


@NgModule({
  declarations: [ClientComponent],
  imports: [
    CommonModule,
    HttpClientModule,
    FormsModule,
    RouterModule.forChild(routes),
    NgxPaginationModule,
    ReactiveFormsModule,
    PdfViewerModule
  ]
})
export class ClientModule {
  constructor()
  {
    console.log("ClientModule")
  }
 }
