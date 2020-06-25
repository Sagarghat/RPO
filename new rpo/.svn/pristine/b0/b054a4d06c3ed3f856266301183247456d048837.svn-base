import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { PdfViewerModule } from 'ng2-pdf-viewer';
import { HttpClientModule } from '@angular/common/http';
import { ServiceService } from './services/service.service';
import { CommonModule } from '@angular/common';
import { PaginationModule } from 'ngx-bootstrap/pagination';
import { ToastrModule } from 'ng6-toastr-notifications';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { RichTextEditorModule } from '@syncfusion/ej2-angular-richtexteditor';
import { NgxPaginationModule } from 'ngx-pagination';


@NgModule({
  declarations: [
    AppComponent,
 
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    CommonModule,
    ReactiveFormsModule,
    HttpClientModule,
    PaginationModule.forRoot(),
    ToastrModule.forRoot(),
    BrowserAnimationsModule,
    RichTextEditorModule,NgxPaginationModule,
    PdfViewerModule,
  ],
  providers: [ServiceService],
  bootstrap: [AppComponent]
})
export class AppModule {
  constructor() {
    console.log('AppModule');
  }
}
